package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestObtenerCorrelativo;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseObtenerCorrelativo;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces.ISerieDocumentoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.model.SerieDocumentoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class SerieDocumentoDetalleRepository implements ISerieDocumentoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleSerieDocumento DetalleSerieDocumento(RequestDetalleSeries request) {
        ResponseDetalleSerieDocumento response = new ResponseDetalleSerieDocumento();
        String SQL = "{ call CONFIGURACION.sp_ObtenerSerieDocumentoPorId(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdSeries());
            Long sucursalId = 1L;
            Long empresaId = 1L;
            pstmt.setLong(2, sucursalId);
            pstmt.setLong(3, empresaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SerieDocumentoModel item = new SerieDocumentoModel();
                    item.setIdSerieDocumento(rs.getLong("idSerieDocumento"));
                    item.setIdTipoDocumento(rs.getLong("idTipoComprobante"));
                    item.setSerie(rs.getString("serie"));
                    item.setCorrelativoActual(rs.getLong("correlativoActual"));
                    item.setEsElectronico(rs.getInt("esElectronico"));
                    item.setEstado(rs.getInt("estado"));

                    response.setExito(true);
                    response.setMessage("SerieDocumento obtenido correctamente.");
                    response.setSerieDocumento(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró SerieDocumento.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_ObtenerSerieDocumentoPorId", e);
        }
        return response;
    }

    @Override
    public ResponseObtenerCorrelativo ObtenerCorelativo(RequestObtenerCorrelativo request) {
        ResponseObtenerCorrelativo response = new ResponseObtenerCorrelativo();
        String SQL = "{ call CONFIGURACION.sp_ObtenerSiguienteCorrelativo(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdSerieDocumento());
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);

            pstmt.registerOutParameter(4, Types.VARCHAR);

            pstmt.executeUpdate();

            String numeroCorrelativoFormateado = pstmt.getString(4);

            if (numeroCorrelativoFormateado != null && !numeroCorrelativoFormateado.trim().isEmpty()) {
                response.setExito(true);
                response.setMessage("Siguiente correlativo generado con éxito.");
                response.setCorrelativo(numeroCorrelativoFormateado);
            } else {
                response.setExito(false);
                response.setMessage("No se pudo recuperar el correlativo formateado.");
            }

        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error crítico en CONFIGURACION.sp_ObtenerSiguienteCorrelativo", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
