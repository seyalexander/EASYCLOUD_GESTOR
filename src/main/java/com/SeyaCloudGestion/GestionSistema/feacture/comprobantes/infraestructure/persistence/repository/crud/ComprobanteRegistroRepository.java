package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces.IComprobanteRegistro;
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
public class ComprobanteRegistroRepository implements IComprobanteRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroComprobante RegistroComprobante(RequestRegistroComprobante request) {
        ResponseRegistroComprobante rpt = new ResponseRegistroComprobante();
        String SQL = "{ call VENTAS.sp_RegistroComprobante(?,?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdVenta());
            setParameter(pstmt, 2, request.getIdTipoDocumento());
            setParameter(pstmt, 3, request.getIdSerieDocumento());
            setParameter(pstmt, 4, request.getNumero());
            setParameter(pstmt, 5, request.getFechaEmision());
            setParameter(pstmt, 6, request.getUrlXml());
            setParameter(pstmt, 7, request.getUrlPdf());
            setParameter(pstmt, 8, request.getEstado());
            setParameter(pstmt, 9, request.getFechaIngreso());
            Long userId = 1L;
            pstmt.setLong(10, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Comprobante insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Comprobante.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroComprobante", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
