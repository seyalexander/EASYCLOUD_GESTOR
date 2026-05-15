package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseRegistroEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestRegistroUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseRegistroUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces.IUsuarioRegistro;
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
public class UsuarioRegistroRepository implements IUsuarioRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseRegistroUsuario registrarUsuario(RequestRegistroUsuario request) {
        ResponseRegistroUsuario rpt = new ResponseRegistroUsuario();

        String SQL = "{ call SEGURIDAD.sp_RegistroUsuario(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new RuntimeException("Usuario no autenticado");
//            }
//            Long userId =  authentication.getPrincipal();

            Long userId = 1L;

            pstmt.setString(1, request.getUsuario());
            pstmt.setString(2, request.getPassowrd());
            pstmt.setLong(3, request.getIdRol());
            pstmt.setLong(4, request.getIdEmpleado());
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Usuario insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el usuario.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }

}
