package com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestDetalleEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseDetalleEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces.IEmpleadoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.model.EmpleadoModel;
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
public class EmpleadoDetalleRepository implements IEmpleadoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseDetalleEmpleado DetalleEmpleado(RequestDetalleEmpleado request) {
        ResponseDetalleEmpleado response = new ResponseDetalleEmpleado();
        EmpleadoModel empleado = null;

        String SQL = "{ call SEGURIDAD.sp_ObtenerEmpleadoPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdEmpleado());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    empleado = new EmpleadoModel();
                    empleado.setIdEmpleado(rs.getLong("idEmpleado"));
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setApellido(rs.getString("apellido"));
                    empleado.setEstado(rs.getInt("estado"));
                    empleado.setTelefono(rs.getString("telefono"));
                    empleado.setImagenUrl(rs.getString("imagenUrl"));
                    empleado.setIdTipoDocumento(rs.getLong("id_tipoDocumento"));
                    empleado.setTipOocumento(rs.getString("tipoDocumento"));
                    empleado.setDocumento(rs.getString("documento"));
                    empleado.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    empleado.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    empleado.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    empleado.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    empleado.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    empleado.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("Empleado obtenido correctamente");
                    response.setEmpleado(empleado);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró al empleado");
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
