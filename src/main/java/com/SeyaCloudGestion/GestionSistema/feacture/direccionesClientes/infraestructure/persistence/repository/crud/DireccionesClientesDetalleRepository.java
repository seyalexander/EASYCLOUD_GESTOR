package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestDetalleDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseDetalleDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.interfaces.IDireccionesClientesDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.model.DireccionesClientesModel;
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
public class DireccionesClientesDetalleRepository implements IDireccionesClientesDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleDireccionesClientes DetalleDireccionesClientes(RequestDetalleDireccionesClientes request) {
        ResponseDetalleDireccionesClientes response = new ResponseDetalleDireccionesClientes();
        String SQL = "{ call CLIENTES.sp_ObtenerDireccionClientePorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdDireccionesClientes());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DireccionesClientesModel item = new DireccionesClientesModel();
                    item.setIdDireccionCliente(rs.getLong("idDireccionCliente"));
                    item.setIdCliente(rs.getLong("idCliente"));
                    item.setDireccion(rs.getString("direccion"));
                    item.setDepartamento(rs.getString("departamento"));
                    item.setProvincia(rs.getString("provincia"));
                    item.setDistrito(rs.getString("distrito"));
                    item.setReferencia(rs.getString("referencia"));
                    item.setEstado(rs.getInt("estado"));
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
                    response.setMessage("DireccionesClientes obtenido correctamente.");
                    response.setDireccionesClientes(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró DireccionesClientes.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CLIENTES.sp_ObtenerDireccionClientePorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
