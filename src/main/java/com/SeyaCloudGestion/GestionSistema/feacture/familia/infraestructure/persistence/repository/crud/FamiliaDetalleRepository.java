package com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces.IFamiliaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model.FamiliaModel;
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
public class FamiliaDetalleRepository implements IFamiliaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleFamilia DetalleFamilia(RequestDetalleFamilia request) {
        ResponseDetalleFamilia response = new ResponseDetalleFamilia();
        FamiliaModel familia = null;

        String SQL = "{ call PRODUCTOS.sp_ObtenerFamiliaPorId(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdFamilia());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    familia = new FamiliaModel();
                    familia.setIdFamilia(rs.getLong("idFamilia"));
                    familia.setDescripcion(rs.getString("descripcion"));
                    familia.setImagenUrl(rs.getString("imagenUrl"));
                    familia.setEstado(rs.getInt("estado"));
                    familia.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    familia.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    familia.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    familia.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    familia.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    familia.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("Familia obtenida correctamente");
                    response.setFamilia(familia);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró la familia");
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
