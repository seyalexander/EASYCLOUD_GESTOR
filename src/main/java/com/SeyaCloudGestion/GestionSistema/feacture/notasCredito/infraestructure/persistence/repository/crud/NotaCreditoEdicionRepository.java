package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestEditarAllNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestEditarEstadoNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseEditarAllNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseEditarEstadoNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces.INotaCreditoEdicion;
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
public class NotaCreditoEdicionRepository implements INotaCreditoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllNotaCredito EditarAllNotaCredito(RequestEditarAllNotaCredito request) {
        ResponseEditarAllNotaCredito rpt = new ResponseEditarAllNotaCredito();
        String SQL = "{ call VENTAS.sp_EditarNotaCredito(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("NotaCredito actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó NotaCredito.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarNotaCredito", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoNotaCredito EditarEstadoNotaCredito(RequestEditarEstadoNotaCredito request, int estado) {
        ResponseEditarEstadoNotaCredito rpt = new ResponseEditarEstadoNotaCredito();
        String SQL = "{ call VENTAS.sp_EditarNotaCredito_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("NotaCredito actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó NotaCredito.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarNotaCredito_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
