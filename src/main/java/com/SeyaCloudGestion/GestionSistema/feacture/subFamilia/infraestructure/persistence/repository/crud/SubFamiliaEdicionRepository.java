package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarEstadoSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarEstadoSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces.ISubFamiliaEdicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
@Transactional("sqlServerTransactionManager")
public class SubFamiliaEdicionRepository implements ISubFamiliaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllSubFamilia EdicionAllSubFamilia(RequestEditarAllSubFamilia request) {
        ResponseEditarAllSubFamilia rpt = new ResponseEditarAllSubFamilia();

        String SQL = "{ call PRODUCTOS.sp_EditarSubFamilia(?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;
            Long empresaId = 1L;

            pstmt.setLong(1, request.getIdSubFamilia());
            pstmt.setString(2, request.getSubFamiliaDescripcion());
            pstmt.setString(3, request.getImagenUrl());
            pstmt.setLong(4, request.getIdFamilia());
            pstmt.setInt(5, request.getEstado());
            pstmt.setLong(6, userId);
            pstmt.setLong(7, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Sub Familia actualizada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó la sub familia.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            String mensaje = e.getMessage();

            if (mensaje != null && mensaje.contains("UQ_SubFamilia_Familia")) {
                rpt.setMessage("Ya existe una sub familia con esa descripción.");
            }

            else {
                rpt.setMessage("Error al actualizar la sub familia.");
            }
        }

        return rpt;
    }

    @Override
    public ResponseEditarEstadoSubFamilia EditarEstadoSubFamilia(RequestEditarEstadoSubFamilia request, int estado) {
        ResponseEditarEstadoSubFamilia rpt = new ResponseEditarEstadoSubFamilia();

        String SQL = "{ call PRODUCTOS.sp_EditarSubFamilia_Estado(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;
            Long empresaId = 1L;

            pstmt.setLong(1, request.getIdSubFamilia());
            pstmt.setInt(2, estado);
            pstmt.setLong(3, userId);
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Sub Familia actualizada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó la sub familia.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
