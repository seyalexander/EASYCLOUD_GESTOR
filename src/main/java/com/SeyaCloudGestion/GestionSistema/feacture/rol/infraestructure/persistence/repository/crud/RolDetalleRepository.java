package com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestDetalleRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseDetalleRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces.IRolDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.model.RolModel;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.model.subFamiliaModel;
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
public class RolDetalleRepository implements IRolDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseDetalleRol DetalleRol(RequestDetalleRol request) {
        ResponseDetalleRol response = new ResponseDetalleRol();
        RolModel rol = null;

        String SQL = "{ call SEGURIDAD.sp_ObtenerRolPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdRol());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    rol = new RolModel();
                    rol.setIdRol(rs.getLong("idRol"));
                    rol.setDescripcion(rs.getString("descripcion"));
                    rol.setEstado(rs.getInt("estado"));
                    rol.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    rol.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    rol.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    rol.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    rol.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    rol.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("Rol obtenido correctamente");
                    response.setRol(rol);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró el rol");
                }
            }

        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage("Error al obtener el rol");
            log.error("Error en SEGURIDAD.sp_ObtenerRolPorId", e);
        }

        return response;
    }
}
