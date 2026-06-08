package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestDetalleComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseDetalleComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces.IComprobanteDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.model.ComprobanteModel;
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
public class ComprobanteDetalleRepository implements IComprobanteDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleComprobante DetalleComprobante(RequestDetalleComprobante request) {
        ResponseDetalleComprobante response = new ResponseDetalleComprobante();
        String SQL = "{ call VENTAS.sp_ObtenerComprobantePorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ComprobanteModel item = new ComprobanteModel();
                    item.setIdComprobante(rs.getLong("idComprobante"));
                    item.setIdVenta(rs.getLong("idVenta"));
                    item.setIdTipoDocumento(rs.getLong("idTipoDocumento"));
                    item.setIdSerieDocumento(rs.getLong("idSerieDocumento"));
                    item.setNumero(rs.getString("numero"));
                    item.setFechaEmision((rs.getTimestamp("fechaEmision") != null ? rs.getTimestamp("fechaEmision").toLocalDateTime() : null));
                    item.setUrlXml(rs.getString("urlXml"));
                    item.setUrlPdf(rs.getString("urlPdf"));
                    item.setEstado(rs.getString("estado"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("Comprobante obtenido correctamente.");
                    response.setComprobante(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Comprobante.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerComprobantePorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
