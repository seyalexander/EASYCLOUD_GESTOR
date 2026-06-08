package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces.IPagoClienteRegistro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class PagoClienteRegistroRepository implements IPagoClienteRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroPagoCliente RegistroPagoCliente(RequestRegistroPagoCliente request) {
        ResponseRegistroPagoCliente rpt = new ResponseRegistroPagoCliente();
        String SQL = "{ call VENTAS.sp_RegistroPagoCliente(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCuentaPorCobrar());
            setParameter(pstmt, 2, request.getFechaPago());
            setParameter(pstmt, 3, request.getMontoPagado());
            setParameter(pstmt, 4, request.getMetodoPago());
            Long userId = 1L;
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("PagoCliente insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó PagoCliente.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroPagoCliente", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
