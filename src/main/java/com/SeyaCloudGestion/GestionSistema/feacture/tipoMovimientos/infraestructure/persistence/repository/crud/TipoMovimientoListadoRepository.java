package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestListaTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseListaTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.interfaces.ITipoMovimientoListado;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.model.TipoMovimientoModel;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class TipoMovimientoListadoRepository implements ITipoMovimientoListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaTipoMovimiento ListaTipoMovimiento(RequestListaTipoMovimiento request) {
        ResponseListaTipoMovimiento rpt = new ResponseListaTipoMovimiento();
        List<TipoMovimientoModel> registros = new ArrayList<>();
        String SQL = "{ call INVENTARIO.sp_ListarTipoMovimiento(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());
            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    TipoMovimientoModel item = new TipoMovimientoModel();
                item.setIdTipoMovimiento(rs.getLong("idTipoMovimiento"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEsEntrada(rs.getInt("esEntrada"));
                item.setEstado(rs.getInt("estado"));
                item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    item.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    registros.add(item);
            }

            rpt.setExito(true);
            rpt.setTipoMovimientos(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarTipoMovimiento", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
