package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestDetalleDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseDetalleDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces.IDevolucionDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.model.DevolucionModel;
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
public class DevolucionDetalleRepository implements IDevolucionDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleDevolucion DetalleDevolucion(RequestDetalleDevolucion request) {
        ResponseDetalleDevolucion response = new ResponseDetalleDevolucion();
        String SQL = "{ call VENTAS.sp_ObtenerDevolucionPorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DevolucionModel item = new DevolucionModel();
                    item.setIdDevolucion(rs.getLong("idDevolucion"));
                    item.setIdDetalleVenta(rs.getLong("idVenta"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setCantidad(rs.getDouble("cantidad"));
                    item.setMotivo(rs.getString("motivo"));
                    item.setFechaDevolucion((rs.getTimestamp("fechaDevolucion") != null ? rs.getTimestamp("fechaDevolucion").toLocalDateTime() : null));
                    item.setIdUsuario(rs.getLong("idUsuario"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("Devolucion obtenido correctamente.");
                    response.setDevolucion(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Devolucion.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerDevolucionPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
