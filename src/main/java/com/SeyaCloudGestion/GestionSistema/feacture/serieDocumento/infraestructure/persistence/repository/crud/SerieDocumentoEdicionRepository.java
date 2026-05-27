package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarAllSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarEstadoSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseEditarAllSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseEditarEstadoSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces.ISerieDocumentoEdicion;
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
public class SerieDocumentoEdicionRepository implements ISerieDocumentoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllSerieDocumento EditarAllSerieDocumento(RequestEditarAllSeries request) {
        ResponseEditarAllSerieDocumento rpt = new ResponseEditarAllSerieDocumento();
        String SQL = "{ call dbo.sp_EditarSerieDocumento(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            //pstmt.setLong(1, userId);
            pstmt.setLong(1, request.getIdSerieDocumento());
            pstmt.setLong(2, request.getIdTipoDocumento());
            pstmt.setString(3, request.getSerie());
            pstmt.setLong(4, request.getCorrelativoActual());
            pstmt.setInt(5, request.getEsElectronico());
            pstmt.setInt(6, request.getEstado());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("SerieDocumento actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó SerieDocumento.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_EditarSerieDocumento", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoSerieDocumento EditarEstadoSerieDocumento(RequestEditarEstadoSeries request, int estado) {
        ResponseEditarEstadoSerieDocumento rpt = new ResponseEditarEstadoSerieDocumento();
        String SQL = "{ call dbo.sp_EditarSerieDocumento_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdSeries());
            pstmt.setInt(2, estado);
            //Long userId = 1L;
            //pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("SerieDocumento actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó SerieDocumento.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_EditarSerieDocumento_Estado", e);
        }
        return rpt;
    }

}
