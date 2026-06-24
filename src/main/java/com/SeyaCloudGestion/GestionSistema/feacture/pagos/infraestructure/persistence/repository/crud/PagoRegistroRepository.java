package com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces.IPagoRegistro;
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
public class PagoRegistroRepository implements IPagoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroPago RegistroPago(RequestRegistroPago request) {
        ResponseRegistroPago rpt = new ResponseRegistroPago();
        String SQL = "{ call VENTAS.sp_RegistrarPagoCliente(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCuentaPorCobrar());
            setParameter(pstmt, 2, request.getIdTipoPago());
            setParameter(pstmt, 3, request.getMonto());
            Long empresaId = 1L;
            Long sucrusalId = 1L;
            setParameter(pstmt, 4, empresaId);
            setParameter(pstmt, 5, sucrusalId);
            Long userId = 1L;
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Pago insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Pago.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistrarPagoCliente", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
