package com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces.IPagoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.model.PagoModel;
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
public class PagoDetalleRepository implements IPagoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetallePago DetallePago(RequestDetallePago request) {
        ResponseDetallePago response = new ResponseDetallePago();
        String SQL = "{ call VENTAS.sp_ObtenerPagoPorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PagoModel item = new PagoModel();
                    item.setIdPago(rs.getLong("idPago"));
                    item.setIdVenta(rs.getLong("idVenta"));
                    item.setIdTipoPago(rs.getLong("idTipoPago"));
                    item.setMonto(rs.getDouble("monto"));
                    item.setReferencia(rs.getString("referencia"));
                    item.setFechaPago((rs.getTimestamp("fechaPago") != null ? rs.getTimestamp("fechaPago").toLocalDateTime() : null));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));

                    response.setExito(true);
                    response.setMessage("Pago obtenido correctamente.");
                    response.setPago(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Pago.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerPagoPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
