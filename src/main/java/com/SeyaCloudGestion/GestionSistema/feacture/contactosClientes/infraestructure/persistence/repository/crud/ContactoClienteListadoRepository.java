package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestListaContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseListaContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.interfaces.IContactoClienteListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ContactoClienteListadoRepository implements IContactoClienteListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaContactoCliente ListaContactoCliente(RequestListaContactoCliente request) {
        ResponseListaContactoCliente rpt = new ResponseListaContactoCliente();
        List<ContactoClienteModel> registros = new ArrayList<>();
        String SQL = "{ call CLIENTES.sp_ListarContactoCliente(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdCliente());
            pstmt.setInt(2, request.getEstado());

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
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

                    registros.add(item);
            }

            rpt.setExito(true);
            rpt.setContactoClientes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarContactoCliente", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
