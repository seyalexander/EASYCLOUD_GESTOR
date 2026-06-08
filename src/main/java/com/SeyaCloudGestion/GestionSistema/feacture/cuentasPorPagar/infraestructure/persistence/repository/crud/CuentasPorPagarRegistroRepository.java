package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestRegistroCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseRegistroCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces.ICuentasPorPagarRegistro;
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
public class CuentasPorPagarRegistroRepository implements ICuentasPorPagarRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroCuentasPorPagar RegistroCuentasPorPagar(RequestRegistroCuentasPorPagar request) {
        ResponseRegistroCuentasPorPagar rpt = new ResponseRegistroCuentasPorPagar();
        String SQL = "{ call COMPRAS.sp_RegistroCuentasPorPagar(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCompra());
            setParameter(pstmt, 2, request.getMontoPendiente());
            setParameter(pstmt, 3, request.getFechaVencimiento());
            setParameter(pstmt, 4, request.getEstado());
            setParameter(pstmt, 5, request.getFechaIngreso());
            Long userId = 1L;
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CuentasPorPagar insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó CuentasPorPagar.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_RegistroCuentasPorPagar", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
