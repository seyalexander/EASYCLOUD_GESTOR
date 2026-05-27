package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces.ISubFamiliaDetalle;
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
public class SubFamiliaDetalleRepository implements ISubFamiliaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseDetalleSubFamilia DetalleSubFamilia(RequestDetalleSubFamilia request) {
        ResponseDetalleSubFamilia response = new ResponseDetalleSubFamilia();
        subFamiliaModel familia = null;

        String SQL = "{ call PRODUCTOS.sp_ObtenerSubFamiliaPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdSubFamilia());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    familia = new subFamiliaModel();
                    familia.setIdFamilia(rs.getLong("idFamilia"));
                    familia.setFamiliaDescripcion(rs.getString("familiaDescripcion"));
                    familia.setIdSubFamilia(rs.getLong("idSubFamilia"));
                    familia.setSubFamiliaDescripcion(rs.getString("subFamiliaDescripcion"));
                    familia.setEstado(rs.getInt("estado"));
                    familia.setFechaCreacion(rs.getString("fechaCreacion"));
                    familia.setFechaEdicion(rs.getString("fechaEdicion"));
                    familia.setFechaAnulacion(rs.getString("fechaAnulacion"));
                    familia.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    familia.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    familia.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("Sub Familia obtenida correctamente");
                    response.setSubFamilia(familia);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró la sub familia");
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
