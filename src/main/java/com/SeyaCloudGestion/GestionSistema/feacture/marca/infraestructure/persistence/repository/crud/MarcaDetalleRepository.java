package com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.model.MarcaModel;
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
public class MarcaDetalleRepository implements IMarcaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseDetalleMarca DetalleMarca(RequestDetalleMarca request) {
        ResponseDetalleMarca response = new ResponseDetalleMarca();
        MarcaModel marca = null;

        String SQL = "{ call PRODUCTOS.sp_ObtenerMarcaPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdMarca());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    marca = new MarcaModel();
                    marca.setIdMarca(rs.getLong("idMarca"));
                    marca.setDescripcion(rs.getString("descripcion"));
                    marca.setEstado(rs.getInt("estado"));
                    marca.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    marca.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    marca.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    marca.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    marca.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    marca.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("Marca obtenida correctamente");
                    response.setMarca(marca);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró la marca");
                }
            }

        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage("Error al obtener la marca");
            log.error("Error en PRODUCTOS.sp_ObtenerFamiliaPorId", e);
        }

        return response;
    }
}
