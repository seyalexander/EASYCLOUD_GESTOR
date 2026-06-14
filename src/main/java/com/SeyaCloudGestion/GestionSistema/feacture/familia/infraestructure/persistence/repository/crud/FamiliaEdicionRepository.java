package com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarEstadoFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarEstadoFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces.IFamiliaEdicion;
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
public class FamiliaEdicionRepository implements IFamiliaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllFamilia EditarAllFamilia(RequestEditarAllFamilia request) {
        ResponseEditarAllFamilia rpt = new ResponseEditarAllFamilia();

        String SQL = "{ call PRODUCTOS.sp_EditarFamilia(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;
            Long empresaId = 1L;

            pstmt.setLong(1, request.getIdFamilia());
            pstmt.setString(2, request.getDescripcion());
            pstmt.setString(3, request.getImagenUrl());
            pstmt.setInt(4, request.getEstado());
            pstmt.setLong(5, userId);
            pstmt.setLong(6, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Familia actualizada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó la familia.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            String mensaje = e.getMessage();

            if (mensaje != null && mensaje.contains("UQ_Familia_Empresa")) {
                rpt.setMessage("Ya existe una familia con esa descripción.");
            }
            else {
                rpt.setMessage("Error al actualizar la familia.");
            }
        }

        return rpt;
    }

    @Override
    public ResponseEditarEstadoFamilia EditarEstadoFamilia(RequestEditarEstadoFamilia request, int estado) {
        ResponseEditarEstadoFamilia rpt = new ResponseEditarEstadoFamilia();

        String SQL = "{ call PRODUCTOS.sp_EditarFamilia_Estado(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;
            Long empresaId = 1L;

            pstmt.setLong(1, request.getIdFamilia());
            pstmt.setInt(2, estado);
            pstmt.setLong(3, userId);
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Familia actualizada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó la familia.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}