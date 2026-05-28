package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestDetalleContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseDetalleContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.interfaces.IContactoClienteDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.persistence.model.ContactoClienteModel;
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
public class ContactoClienteDetalleRepository implements IContactoClienteDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleContactoCliente DetalleContactoCliente(RequestDetalleContactoCliente request) {
        ResponseDetalleContactoCliente response = new ResponseDetalleContactoCliente();
        String SQL = "{ call CLIENTES.sp_ObtenerContactoClientePorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdContactoCliente());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ContactoClienteModel item = new ContactoClienteModel();
                    item.setIdContactoCliente(rs.getLong("idContactoCliente"));
                    item.setIdCliente(rs.getLong("idCliente"));
                    item.setNombreContacto(rs.getString("nombreContacto"));
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
                    response.setMessage("ContactoCliente obtenido correctamente.");
                    response.setContactoCliente(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró ContactoCliente.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerContactoClientePorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
