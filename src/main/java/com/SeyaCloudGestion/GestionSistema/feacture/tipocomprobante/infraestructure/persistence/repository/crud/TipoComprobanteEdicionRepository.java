package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.repository.crud;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestEditarAllTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestEditarEstadoTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseEditarAllTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseEditarEstadoTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces.ITipoComprobanteEdicion;
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
public class TipoComprobanteEdicionRepository implements ITipoComprobanteEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllTipoComprobante EditarAllTipoComprobante(RequestEditarAllTipoComprobante request) {
        ResponseEditarAllTipoComprobante rpt = new ResponseEditarAllTipoComprobante();

        String SQL = "{ call CONFIGURACION.sp_EditarTipoComprobante(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;

            pstmt.setLong(1, request.getIdTipoComprobante());
            pstmt.setString(2, request.getDescripcion());
            pstmt.setString(3, request.getCodigoSunat());
            pstmt.setInt(4, request.getEstado());
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Tipo de comprobante actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó el tipo de comprobante.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un tipo comprobante con esa descripción.");
            } else {
                rpt.setMessage("Error al actualizar el tipo comprobante.");
            }
            log.error("Error en CONFIGURACION.sp_EditarTipoComprobante", e);
        }

        return rpt;
    }

    @Override
    public ResponseEditarEstadoTipoComprobante EditarEstadoTipoComprobante(RequestEditarEstadoTipoComprobante request, int estado) {
        ResponseEditarEstadoTipoComprobante rpt = new ResponseEditarEstadoTipoComprobante();

        String SQL = "{ call CONFIGURACION.sp_EditarTipoComprobante_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;

            pstmt.setLong(1, request.getIdTipoComprobante());
            pstmt.setInt(2, estado);
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Tipo de comprobante actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó el tipo de comprobante.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}