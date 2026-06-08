package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestRegistroNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseRegistroNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces.INotaCreditoRegistro;
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
public class NotaCreditoRegistroRepository implements INotaCreditoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroNotaCredito RegistroNotaCredito(RequestRegistroNotaCredito request) {
        ResponseRegistroNotaCredito rpt = new ResponseRegistroNotaCredito();
        String SQL = "{ call VENTAS.sp_RegistroNotaCredito(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdVenta());
            setParameter(pstmt, 2, request.getMotivo());
            setParameter(pstmt, 3, request.getFechaEmision());
            setParameter(pstmt, 4, request.getTotal());
            setParameter(pstmt, 5, request.getFechaIngreso());
            Long userId = 1L;
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("NotaCredito insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó NotaCredito.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroNotaCredito", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
