package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces.IPagoClienteDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.model.PagoClienteModel;
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
public class PagoClienteDetalleRepository implements IPagoClienteDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetallePagoCliente DetallePagoCliente(RequestDetallePagoCliente request) {
        ResponseDetallePagoCliente response = new ResponseDetallePagoCliente();
        String SQL = "{ call VENTAS.sp_ObtenerPagoClientePorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PagoClienteModel item = new PagoClienteModel();
                    item.setIdPagoCliente(rs.getLong("idPagoCliente"));
                    item.setIdCuentaPorCobrar(rs.getLong("idCuentaPorCobrar"));
                    item.setFechaPago((rs.getTimestamp("fechaPago") != null ? rs.getTimestamp("fechaPago").toLocalDateTime() : null));
                    item.setMontoPagado(rs.getDouble("montoPagado"));
                    item.setMetodoPago(rs.getString("metodoPago"));
                    response.setExito(true);
                    response.setMessage("PagoCliente obtenido correctamente.");
                    response.setPagoCliente(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró PagoCliente.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerPagoClientePorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
