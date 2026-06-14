package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseEditarAllEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestEditarAllTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestEditarEstadoTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseEditarAllTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseEditarEstadoTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces.ITipoDocumentoEdicion;
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
public class TipoDocumentoEdicionRepository implements ITipoDocumentoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseEditarAllTipoDocumento EditarTipoDocumento(RequestEditarAllTipoDocumento request, long userAutenticado) {
        ResponseEditarAllTipoDocumento rpt = new ResponseEditarAllTipoDocumento();

        String SQL = "{ call CONFIGURACION.sp_EditarTipoDocumentoIdentidad(?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTipoDocumento());
            pstmt.setString(2, request.getDescripcion());
            pstmt.setInt(3, request.getEstado());
            pstmt.setString(4, request.getCodigoSunat());
            pstmt.setInt(5, request.getTipoCaracter().getCodigo());
            pstmt.setInt(6, request.getLongitudMin());
            pstmt.setInt(7, request.getLongitudMax());
            pstmt.setLong(8, userAutenticado);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Tipo Documento actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó el Tipo Documento.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un tipo documento con esa descripcion.");
            } else {
                rpt.setMessage("Error al actualizar el tipo documento.");
            }
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }

    @Override
    public ResponseEditarEstadoTipoDocumento EditarEstadoTipoDocumento(RequestEditarEstadoTipoDocumento request, int estado, long userAutenticado) {
        ResponseEditarEstadoTipoDocumento rpt = new ResponseEditarEstadoTipoDocumento();

        String SQL = "{ call CONFIGURACION.sp_EditarTipoDocumentoIdentidad_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTipoDocumento());
            pstmt.setInt(2, estado);
            pstmt.setLong(3, userAutenticado);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Tipo Documento actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó el Tipo Documento.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
