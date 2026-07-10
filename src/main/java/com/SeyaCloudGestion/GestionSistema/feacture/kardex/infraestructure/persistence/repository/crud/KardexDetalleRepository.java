package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces.IKardexDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.KardexModel;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class KardexDetalleRepository implements IKardexDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleKardex DetalleKardex(RequestDetalleKardex request) {
        ResponseDetalleKardex response = new ResponseDetalleKardex();
        String SQL = "{ call INVENTARIO.sp_ObtenerUltimoSaldoKardex(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            Long empresaId = 1L;
            Long sucursalId = 1L;
            Long almacenId = 1L;
            setParameter(pstmt, 1, request.getIdArticulo());
            pstmt.setLong(2, request.getIdAlmacen());
            pstmt.setLong(3, empresaId);
            pstmt.setLong(4, sucursalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
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

                    response.setExito(true);
                    response.setMessage("Kardex obtenido correctamente.");
                    response.setKardex(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Kardex.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ObtenerUltimoSaldoKardex", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
