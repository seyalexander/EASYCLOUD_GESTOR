package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestRegistroCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseRegistroCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.interfaces.ICuentasPorCobrarRegistro;
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
public class CuentasPorCobrarRegistroRepository implements ICuentasPorCobrarRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroCuentasPorCobrar RegistroCuentasPorCobrar(RequestRegistroCuentasPorCobrar request) {
        ResponseRegistroCuentasPorCobrar rpt = new ResponseRegistroCuentasPorCobrar();
        String SQL = "{ call VENTAS.sp_RegistroCuentaPorCobrar(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdVenta());
            setParameter(pstmt, 2, request.getMontoPendiente());
            setParameter(pstmt, 3, request.getFechaVencimiento());
            Long userId = 1L;
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(4, empresaId);
            pstmt.setLong(5, sucursalId);
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CuentasPorCobrar insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó CuentasPorCobrar.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroCuentasPorCobrar", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
