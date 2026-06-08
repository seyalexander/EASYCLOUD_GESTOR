package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestRegistrarSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseRegistroSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces.ISubFamiliaRegistro;
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
public class SubFamiliaRegistroRepository implements ISubFamiliaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroSubFamilia RegistroSubFamilia(RequestRegistrarSubFamilia request) {
        ResponseRegistroSubFamilia rpt = new ResponseRegistroSubFamilia();

        String SQL = "{ call PRODUCTOS.sp_RegistroSubFamilia(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;
            Long empresaId = 1L;
            pstmt.setString(1, request.getSubFamiliaDescripcion());
            pstmt.setString(2, request.getImagenUrl());
            pstmt.setLong(3, request.getIdFamilia());
            pstmt.setLong(4, userId);
            pstmt.setLong(5, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Sub Familia insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó la sub familia.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);

            String mensaje = e.getMessage();

            if (mensaje != null && mensaje.contains("UQ_SubFamilia_Familia")) {
                rpt.setMessage("Ya existe una sub familia con esa descripción.");
            }

            else {
                rpt.setMessage("Error al registrar la sub familia.");
            }
        }

        return rpt;
    }
}
