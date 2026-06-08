package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestEditarAllComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestEditarEstadoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseEditarAllComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseEditarEstadoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces.IComprobanteEdicion;
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
public class ComprobanteEdicionRepository implements IComprobanteEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllComprobante EditarAllComprobante(RequestEditarAllComprobante request) {
        ResponseEditarAllComprobante rpt = new ResponseEditarAllComprobante();
        String SQL = "{ call VENTAS.sp_EditarComprobante(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Comprobante actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Comprobante.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarComprobante", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoComprobante EditarEstadoComprobante(RequestEditarEstadoComprobante request, int estado) {
        ResponseEditarEstadoComprobante rpt = new ResponseEditarEstadoComprobante();
        String SQL = "{ call VENTAS.sp_EditarComprobante_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Comprobante actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Comprobante.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarComprobante_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
