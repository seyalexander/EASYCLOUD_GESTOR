package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces.ISerieDocumentoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.model.SerieDocumentoModel;
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

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
