package com.SeyaCloudGestion.GestionSistema.feacture.reportes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestEditarAllReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestEditarEstadoReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseEditarAllReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseEditarEstadoReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.domain.interfaces.IReportesEdicion;
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
public class ReportesEdicionRepository implements IReportesEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllReportes EditarAllReportes(RequestEditarAllReportes request) {
        ResponseEditarAllReportes rpt = new ResponseEditarAllReportes();
        String SQL = "{ call dbo.sp_EditarReportes(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Reportes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Reportes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_EditarReportes", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoReportes EditarEstadoReportes(RequestEditarEstadoReportes request, int estado) {
        ResponseEditarEstadoReportes rpt = new ResponseEditarEstadoReportes();
        String SQL = "{ call dbo.sp_EditarReportes_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdReportes());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Reportes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Reportes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_EditarReportes_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
