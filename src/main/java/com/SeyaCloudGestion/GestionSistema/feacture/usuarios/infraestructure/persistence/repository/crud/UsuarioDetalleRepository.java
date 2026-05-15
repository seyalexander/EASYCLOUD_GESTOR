package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseDetalleEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.model.EmpleadoModel;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestDetalleUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseDetalleUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.interfaces.IUsuarioDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.model.UsuariosModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class UsuarioDetalleRepository implements IUsuarioDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseDetalleUsuario DetalleUsuario(RequestDetalleUsuario request) {
        ResponseDetalleUsuario response = new ResponseDetalleUsuario();
        UsuariosModel usuario = null;

        String SQL = "{ call SEGURIDAD.sp_ObtenerUsuarioPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdUsuario());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuario = new UsuariosModel();
                    usuario.setIdUsuario(rs.getLong("idUsuario"));
                    usuario.setUsuario(rs.getString("usuario"));
                    usuario.setPassowrd(rs.getString("password"));
                    usuario.setIdEmpleado(rs.getLong("idEmpleado"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
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

                    response.setExito(true);
                    response.setMessage("Usuario obtenido correctamente");
                    response.setUsuario(usuario);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró al usuario");
                }
            }

        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage("Error al obtener la familia");
            log.error("Error en PRODUCTOS.sp_ObtenerFamiliaPorId", e);
        }

        return response;
    }
}
