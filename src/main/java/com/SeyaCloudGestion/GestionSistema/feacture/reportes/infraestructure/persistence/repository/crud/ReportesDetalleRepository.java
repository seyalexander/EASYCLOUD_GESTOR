package com.SeyaCloudGestion.GestionSistema.feacture.reportes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestDetalleReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseDetalleReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.domain.interfaces.IReportesDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.infraestructure.persistence.model.ReportesModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ReportesDetalleRepository implements IReportesDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleReportes DetalleReportes(RequestDetalleReportes request) {
        ResponseDetalleReportes response = new ResponseDetalleReportes();
        String SQL = "{ call dbo.sp_ObtenerReportesPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdReportes());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ReportesModel item = new ReportesModel();

                    response.setExito(true);
                    response.setMessage("Reportes obtenido correctamente.");
                    response.setReportes(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Reportes.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en dbo.sp_ObtenerReportesPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
