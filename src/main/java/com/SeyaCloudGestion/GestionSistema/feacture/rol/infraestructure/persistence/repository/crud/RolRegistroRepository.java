package com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces.IRolRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseRegistroSubFamilia;
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
public class RolRegistroRepository implements IRolRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseRegistroRol registrarRol(RequestRegistroRol request) {
        ResponseRegistroRol rpt = new ResponseRegistroRol();

        String SQL = "{ call SEGURIDAD.sp_RegistroRol(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;

            pstmt.setString(1, request.getDescripcion());
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Rol insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el rol.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
