package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestRegistroDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseRegistroDetallePagoCliente;
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
    public ResponseRegistroDetallePagoCliente RegistroDetallePagoCliente(long idCuentaPorCobrar , RequestRegistroDetallePagoCliente request) {
        ResponseRegistroDetallePagoCliente rpt = new ResponseRegistroDetallePagoCliente();
        String SQL = "{ call VENTAS.sp_RegistrarPagoCliente(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, idCuentaPorCobrar);
            setParameter(pstmt, 2, request.getMontoPagado());
            setParameter(pstmt, 3, request.getIdTipoPago());
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);
            Long sucursalId = 1L;
            pstmt.setLong(5, sucursalId);
            Long userId = 1L;
            pstmt.setLong(6, userId);

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
