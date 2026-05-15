package com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestListaRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseListaRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.interfaces.IRolListado;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.model.RolModel;
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
public class RolListadoRepository implements IRolListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseListaRol ListaRol(RequestListaRol request) {
        ResponseListaRol rpt = new ResponseListaRol();
        List<RolModel> familias = new ArrayList<>();

        String SQL = "{ call SEGURIDAD.sp_ListarRol (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RolModel rol = new RolModel();

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

                familias.add(rol);
            }
            rpt.setExito(true);
            rpt.setRoles(familias);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
