package com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestRegistroCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseRegistroCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces.IClienteRegistro;
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
public class ClienteRegistroRepository implements IClienteRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroCliente RegistroCliente(RequestRegistroCliente request) {
        ResponseRegistroCliente rpt = new ResponseRegistroCliente();
        String SQL = "{ call VENTAS.sp_RegistroCliente(?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getNombres());
            setParameter(pstmt, 2, request.getApellidos());
            setParameter(pstmt, 3, request.getRazonSocial());
            setParameter(pstmt, 4, request.getNumeroDocumento());
            setParameter(pstmt, 5, request.getIdTipoDocumento());
            setParameter(pstmt, 6, request.getIdTipoCliente());
            setParameter(pstmt, 7, request.getTelefono());
            setParameter(pstmt, 8, request.getEmail());
            Long userId = 1L;
            pstmt.setLong(9, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Cliente insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Cliente.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroCliente", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
