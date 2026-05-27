package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestRegistroContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseRegistroContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.interfaces.IContactoClienteRegistro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ContactoClienteRegistroRepository implements IContactoClienteRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroContactoCliente RegistroContactoCliente(RequestRegistroContactoCliente request) {
        ResponseRegistroContactoCliente rpt = new ResponseRegistroContactoCliente();
        String SQL = "{ call VENTAS.sp_RegistroContactoCliente(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCliente());
            setParameter(pstmt, 2, request.getNombreContacto());
            setParameter(pstmt, 3, request.getTelefono());
            setParameter(pstmt, 4, request.getEmail());
            Long userId = 1L;
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ContactoCliente insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó ContactoCliente.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroContactoCliente", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
