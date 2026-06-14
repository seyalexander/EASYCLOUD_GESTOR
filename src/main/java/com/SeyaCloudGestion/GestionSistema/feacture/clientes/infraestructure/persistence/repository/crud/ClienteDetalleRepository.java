package com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces.IClienteDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.model.ClienteModel;
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
public class ClienteDetalleRepository implements IClienteDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleCliente DetalleCliente(RequestDetalleCliente request) {
        ResponseDetalleCliente response = new ResponseDetalleCliente();
        String SQL = "{ call CLIENTES.sp_ObtenerClientePorId(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdCliente());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ClienteModel item = new ClienteModel();
                    item.setIdCliente(rs.getLong("idCliente"));
                    item.setNombres(rs.getString("nombres"));
                    item.setApellidos(rs.getString("apellidos"));
                    item.setRazonSocial(rs.getString("razonSocial"));
                    item.setNumeroDocumento(rs.getString("numeroDocumento"));
                    item.setIdTipoDocumento(rs.getLong("idTipoDocumento"));
                    item.setIdTipoCliente(rs.getLong("idTipoCliente"));
                    item.setTelefono(rs.getString("telefono"));
                    item.setEmail(rs.getString("email"));
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
                    response.setMessage("Cliente obtenido correctamente.");
                    response.setCliente(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Cliente.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerClientePorId", e);
        }
        return response;
    }

}
