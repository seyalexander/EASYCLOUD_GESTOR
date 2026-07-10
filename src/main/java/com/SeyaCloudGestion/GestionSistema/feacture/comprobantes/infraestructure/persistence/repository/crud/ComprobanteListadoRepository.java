package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestListaComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseListaComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces.IComprobanteListado;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.model.ComprobanteModel;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.model.EstadoComprobante;
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
public class ComprobanteListadoRepository implements IComprobanteListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaComprobante listaComprobante(RequestListaComprobante request) {
        ResponseListaComprobante rpt = new ResponseListaComprobante();
        List<ComprobanteModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarComprobante() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ComprobanteModel item = new ComprobanteModel();
                item.setIdComprobante(rs.getLong("idComprobante"));
                item.setIdVenta(rs.getLong("idVenta"));
                item.setIdTipoDocumento(rs.getLong("idTipoDocumento"));
                item.setIdSerieDocumento(rs.getLong("idSerieDocumento"));
                item.setNumero(rs.getString("numero"));
                item.setFechaEmision((rs.getTimestamp("fechaEmision") != null ? rs.getTimestamp("fechaEmision").toLocalDateTime() : null));
                item.setUrlXml(rs.getString("urlXml"));
                item.setUrlPdf(rs.getString("urlPdf"));
                    String estadoBD = rs.getString("estado");
                    if (estadoBD != null) {
                        item.setEstado(EstadoComprobante.valueOf(estadoBD.toUpperCase().trim()));
                    }
                item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setComprobantes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarComprobante", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
