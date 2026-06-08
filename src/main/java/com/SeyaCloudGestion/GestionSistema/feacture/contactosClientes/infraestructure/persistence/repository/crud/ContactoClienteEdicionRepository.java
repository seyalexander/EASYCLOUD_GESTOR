package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestEditarAllContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestEditarEstadoContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseEditarAllContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseEditarEstadoContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.interfaces.IContactoClienteEdicion;
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
public class ContactoClienteEdicionRepository implements IContactoClienteEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllContactoCliente EditarAllContactoCliente(RequestEditarAllContactoCliente request) {
        ResponseEditarAllContactoCliente rpt = new ResponseEditarAllContactoCliente();
        String SQL = "{ call CLIENTES.sp_EditarContactoCliente(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdContactoCliente());
            pstmt.setString(2, request.getNombreContacto());
            pstmt.setString(3, request.getTelefono());
            pstmt.setString(4, request.getEmail());
            pstmt.setInt(5, request.getEstado());
            Long userId = 1L;
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ContactoCliente actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó ContactoCliente.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarContactoCliente", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoContactoCliente EditarEstadoContactoCliente(RequestEditarEstadoContactoCliente request, int estado) {
        ResponseEditarEstadoContactoCliente rpt = new ResponseEditarEstadoContactoCliente();
        String SQL = "{ call CLIENTES.sp_EditarContactoCliente_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1, request.getIdContactoCliente());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ContactoCliente actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó ContactoCliente.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CLIENTES.sp_EditarContactoCliente_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
