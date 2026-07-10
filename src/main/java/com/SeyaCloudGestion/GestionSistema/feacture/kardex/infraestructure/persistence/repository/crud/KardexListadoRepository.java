package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces.IKardexListado;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.KardexModel;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class KardexListadoRepository implements IKardexListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaKardex listaKardex(RequestListaKardex request) {
        ResponseListaKardex rpt = new ResponseListaKardex();
        List<KardexModel> registros = new ArrayList<>();
        String SQL = "{ call INVENTARIO.sp_ListarKardexPorArticulo(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId = 1L;
            Long sucursalId = 1L;
            Long almacenId = 1L;

            pstmt.setLong(1, empresaId);
            pstmt.setLong(2, sucursalId);
            pstmt.setLong(3, almacenId);
            setParameter(pstmt, 4, request.getIdArticulo());
            pstmt.setTimestamp(
                    5,
                    request.getFechaInicio() != null
                            ? Timestamp.valueOf(request.getFechaInicio().atStartOfDay())
                            : null
            );

            pstmt.setTimestamp(
                    6,
                    request.getFechaFin() != null
                            ? Timestamp.valueOf(request.getFechaFin().plusDays(1).atStartOfDay())
                            : null
            );

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    KardexModel item = new KardexModel();

                    item.setIdKardex(rs.getLong("idKardex"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setIdAlmacen(rs.getLong("idAlmacen"));
                    item.setFecha(
                            rs.getTimestamp("fecha") != null
                                    ? rs.getTimestamp("fecha").toLocalDateTime()
                                    : null
                    );
                    item.setTipoMovimiento(
                            TipoMovimientoKardex.valueOf(rs.getString("tipoMovimiento"))
                    );
                    item.setCantidadEntrada(rs.getDouble("cantidadEntrada"));
                    item.setCostoEntrada(rs.getDouble("costoEntrada"));
                    item.setCantidadSalida(rs.getDouble("cantidadSalida"));
                    item.setCostoSalida(rs.getDouble("costoSalida"));
                    item.setSaldoCantidad(rs.getDouble("saldoCantidad"));
                    item.setSaldoCosto(rs.getDouble("saldoCosto"));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    registros.add(item);
            }

            rpt.setExito(true);
            rpt.setKardex(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarKardex", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
