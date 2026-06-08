package com.SeyaCloudGestion.GestionSistema.feacture.reportes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestRegistroReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseRegistroReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.domain.interfaces.IReportesRegistro;
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
public class ReportesRegistroRepository implements IReportesRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroReportes RegistroReportes(RequestRegistroReportes request) {
        ResponseRegistroReportes rpt = new ResponseRegistroReportes();
        String SQL = "{ call dbo.sp_RegistroReportes(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Reportes insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Reportes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_RegistroReportes", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
