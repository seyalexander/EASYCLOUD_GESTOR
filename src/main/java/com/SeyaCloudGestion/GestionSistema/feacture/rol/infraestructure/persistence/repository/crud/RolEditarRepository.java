package com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarEstadoRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseEditarEstadoRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces.IRolEdicion;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarAllSubFamilia;
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
public class RolEditarRepository implements IRolEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseEditarAllRol EditarRol(RequestEditarAllRol request) {
        ResponseEditarAllRol rpt = new ResponseEditarAllRol();
        String SQL = "{ call SEGURIDAD.sp_EditarRol(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;

            pstmt.setLong(1, request.getIdRol());
            pstmt.setString(2, request.getDescripcion());
            pstmt.setInt(3, request.getEstado());
            pstmt.setLong(4, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Rol actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó el rol.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }

    @Override
    public ResponseEditarEstadoRol EditarEstadoRol(RequestEditarEstadoRol request, int estado) {
        ResponseEditarEstadoRol rpt = new ResponseEditarEstadoRol();
        String SQL = "{ call SEGURIDAD.sp_EditarRol_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;

            pstmt.setLong(1, request.getIdRol());
            pstmt.setInt(2, estado);
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Rol actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó el rol.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
