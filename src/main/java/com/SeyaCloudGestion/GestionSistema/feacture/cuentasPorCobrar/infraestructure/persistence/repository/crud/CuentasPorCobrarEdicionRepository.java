package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestAbonarCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestAnularCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseAbonarCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseAnularCuentaPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.interfaces.ICuentasPorCobrarEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
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
public class CuentasPorCobrarEdicionRepository implements ICuentasPorCobrarEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseAbonarCuentaPorCobrar AbonarCuentasPorCobrar(RequestAbonarCuentaPorCobrar request, EstadoCuenta estado,double monteoPendienteActual) {
        ResponseAbonarCuentaPorCobrar rpt = new ResponseAbonarCuentaPorCobrar();
        String SQL = "{ call VENTAS.sp_ActualizarCuentaPorCobrar(?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            setParameter(pstmt, 1, request.getIdCuentaPorCobrar());
            setParameter(pstmt, 2, monteoPendienteActual);
            setParameter(pstmt, 3, request.getFechaVencimiento());
            String estadoString = (estado != null) ? estado.name() : null;
            setParameter(pstmt, 4, estadoString);
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(5, empresaId);
            pstmt.setLong(6, sucursalId);
            Long userId = 1L;
            pstmt.setLong(7, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CuentasPorCobrar abonado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se abonado CuentasPorCobrar.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarCuentasPorCobrar", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
