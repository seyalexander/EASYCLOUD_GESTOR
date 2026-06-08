package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces.IAlmacenesDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenesModel;
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
public class AlmacenesDetalleRepository implements IAlmacenesDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleAlmacenes DetalleAlmacenes(RequestDetalleAlmacenes request) {
        ResponseDetalleAlmacenes response = new ResponseDetalleAlmacenes();
        String SQL = "{ call INVENTARIO.sp_ObtenerAlmacenesPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdAlmacenes());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AlmacenesModel item = new AlmacenesModel();
                    item.setIdAlmacenes(rs.getLong("idAlmacenes"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setEstado(rs.getInt("estado"));
                    item.setIdSucursales(rs.getLong("idSucursales"));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    item.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("Almacenes obtenido correctamente.");
                    response.setAlmacenes(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Almacenes.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ObtenerAlmacenesPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
