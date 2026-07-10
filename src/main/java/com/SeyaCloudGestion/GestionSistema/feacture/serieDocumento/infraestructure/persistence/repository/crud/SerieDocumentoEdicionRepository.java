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
        String SQL = "{ call CONFIGURACION.sp_EditarSerieDocumento(?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, request.getIdSerieDocumento());
            pstmt.setString(2, request.getSerie());
            pstmt.setInt(3, request.getEsElectronico());
            pstmt.setInt(4, request.getEstado());
            pstmt.setLong(5, userId);
            Long sucursalId = 1L;
            Long empresaId = 1L;
            pstmt.setLong(6, sucursalId);
            pstmt.setLong(7, empresaId);

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
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe una serie con esa descripción.");
            } else {
                rpt.setMessage("Error al actualizar la serie.");
            }
            log.error("Error en CONFIGURACION.sp_EditarSerieDocumento", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoSerieDocumento EditarEstadoSerieDocumento(RequestEditarEstadoSeries request, int estado) {
        ResponseEditarEstadoSerieDocumento rpt = new ResponseEditarEstadoSerieDocumento();
        String SQL = "{ call CONFIGURACION.sp_EditarSerieDocumento_Estado(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdSeries());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long sucursalId = 1L;
            Long empresaId = 1L;
            pstmt.setLong(4, sucursalId);
            pstmt.setLong(5, empresaId);

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
            log.error("Error en CONFIGURACION.sp_EditarSerieDocumento_Estado", e);
        }
        return rpt;
    }

}
