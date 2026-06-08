package com.SeyaCloudGestion.GestionSistema.feacture.reportes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.request.RequestListaReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.application.dto.response.ResponseListaReportes;
import com.SeyaCloudGestion.GestionSistema.feacture.reportes.domain.interfaces.IReportesListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ReportesListadoRepository implements IReportesListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaReportes listaReportes(RequestListaReportes request) {
        ResponseListaReportes rpt = new ResponseListaReportes();
        List<ReportesModel> registros = new ArrayList<>();
        String SQL = "{ call dbo.sp_ListarReportes(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ReportesModel item = new ReportesModel();

                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setReportes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_ListarReportes", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
