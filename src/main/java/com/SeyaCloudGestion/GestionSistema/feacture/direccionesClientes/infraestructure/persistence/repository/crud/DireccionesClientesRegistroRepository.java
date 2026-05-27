package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestRegistroDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseRegistroDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.interfaces.IDireccionesClientesRegistro;
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
public class DireccionesClientesRegistroRepository implements IDireccionesClientesRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroDireccionesClientes RegistroDireccionesClientes(RequestRegistroDireccionesClientes request) {
        ResponseRegistroDireccionesClientes rpt = new ResponseRegistroDireccionesClientes();
        String SQL = "{ call VENTAS.sp_RegistroDireccionesClientes(?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCliente());
            setParameter(pstmt, 2, request.getDireccion());
            setParameter(pstmt, 3, request.getDepartamento());
            setParameter(pstmt, 4, request.getProvincia());
            setParameter(pstmt, 5, request.getDistrito());
            setParameter(pstmt, 6, request.getReferencia());
            Long userId = 1L;
            pstmt.setLong(7, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("DireccionesClientes insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó DireccionesClientes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroDireccionesClientes", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
