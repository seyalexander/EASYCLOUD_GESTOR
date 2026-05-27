package com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestListaCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseListaCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces.IClienteListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ClienteListadoRepository implements IClienteListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaCliente listaCliente(RequestListaCliente request) {
        ResponseListaCliente rpt = new ResponseListaCliente();
        List<ClienteModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarCliente() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
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
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setClientes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarCliente", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
