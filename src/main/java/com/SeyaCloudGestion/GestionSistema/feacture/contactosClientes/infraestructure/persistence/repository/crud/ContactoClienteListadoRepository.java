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
    public ResponseListaContactoCliente listaContactoCliente(RequestListaContactoCliente request) {
        ResponseListaContactoCliente rpt = new ResponseListaContactoCliente();
        List<ContactoClienteModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarContactoCliente() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ContactoClienteModel item = new ContactoClienteModel();
                item.setIdContactoCliente(rs.getLong("idContactoCliente"));
                item.setIdCliente(rs.getLong("idCliente"));
                item.setNombreContacto(rs.getString("nombreContacto"));
                item.setTelefono(rs.getString("telefono"));
                item.setEmail(rs.getString("email"));
                item.setEstado(rs.getInt("estado"));
                    registros.add(item);
                }
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
