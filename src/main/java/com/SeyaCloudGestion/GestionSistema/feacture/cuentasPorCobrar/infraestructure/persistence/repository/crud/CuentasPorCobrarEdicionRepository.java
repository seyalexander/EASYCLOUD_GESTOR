package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestEditarAllCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestEditarEstadoCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseEditarAllCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseEditarEstadoCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.interfaces.ICuentasPorCobrarEdicion;
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
    public ResponseEditarAllCuentasPorCobrar EditarAllCuentasPorCobrar(RequestEditarAllCuentasPorCobrar request) {
        ResponseEditarAllCuentasPorCobrar rpt = new ResponseEditarAllCuentasPorCobrar();
        String SQL = "{ call VENTAS.sp_EditarCuentasPorCobrar(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CuentasPorCobrar actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó CuentasPorCobrar.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarCuentasPorCobrar", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoCuentasPorCobrar EditarEstadoCuentasPorCobrar(RequestEditarEstadoCuentasPorCobrar request, int estado) {
        ResponseEditarEstadoCuentasPorCobrar rpt = new ResponseEditarEstadoCuentasPorCobrar();
        String SQL = "{ call VENTAS.sp_EditarCuentasPorCobrar_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCuentasPorCobrar());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CuentasPorCobrar actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó CuentasPorCobrar.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarCuentasPorCobrar_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
