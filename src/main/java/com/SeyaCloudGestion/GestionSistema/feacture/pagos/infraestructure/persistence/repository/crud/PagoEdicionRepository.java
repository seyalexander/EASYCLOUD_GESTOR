package com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestEditarAllPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestEditarEstadoPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseEditarAllPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseEditarEstadoPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces.IPagoEdicion;
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
public class PagoEdicionRepository implements IPagoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllPago EditarAllPago(RequestEditarAllPago request) {
        ResponseEditarAllPago rpt = new ResponseEditarAllPago();
        String SQL = "{ call VENTAS.sp_EditarPago(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Pago actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Pago.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarPago", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoPago EditarEstadoPago(RequestEditarEstadoPago request, int estado) {
        ResponseEditarEstadoPago rpt = new ResponseEditarEstadoPago();
        String SQL = "{ call VENTAS.sp_EditarPago_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Pago actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Pago.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarPago_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
