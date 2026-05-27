package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestListaUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseListaUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces.IUsuarioListado;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.model.UsuariosModel;
import com.SeyaCloudGestion.GestionSistema.security.PasswordSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional("sqlServerTransactionManager")
public class UsuarioListadoRepository implements IUsuarioListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    private final PasswordSecurityService passwordSecurityService;

    public UsuarioListadoRepository(
            PasswordSecurityService passwordSecurityService
    ){
        this.passwordSecurityService = passwordSecurityService;
    }


    @Override
    public ResponseListaUsuario ListaUsuarios(RequestListaUsuario request) {
        ResponseListaUsuario rpt = new ResponseListaUsuario();
        List<UsuariosModel> usuarios = new ArrayList<>();

        String SQL = "{ call SEGURIDAD.sp_ListarUsuario (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UsuariosModel usuario = new UsuariosModel();

                usuario.setIdUsuario(rs.getLong("idUsuario"));
                usuario.setUsuario(rs.getString("usuario"));

                String passwordEncript = rs.getString("password");
                String passwordDecrypt =  passwordSecurityService.desencriptarPassword(passwordEncript);
                usuario.setPassowrd(passwordDecrypt);

                usuario.setIdEmpleado(rs.getLong("idEmpleado"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setDescripcionRol(rs.getString("descripcionRol"));
                usuario.setEstado(rs.getInt("estado"));
                usuario.setFechaCreacion(
                        rs.getTimestamp("fechaCreacion") != null
                                ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                : null
                );

                usuario.setFechaEdicion(
                        rs.getTimestamp("fechaEdicion") != null
                                ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                : null
                );

                usuario.setFechaAnulacion(
                        rs.getTimestamp("fechaAnulacion") != null
                                ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                : null
                );
                usuario.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                usuario.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                usuario.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                usuarios.add(usuario);
            }
            rpt.setExito(true);
            rpt.setUsuarios(usuarios);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
