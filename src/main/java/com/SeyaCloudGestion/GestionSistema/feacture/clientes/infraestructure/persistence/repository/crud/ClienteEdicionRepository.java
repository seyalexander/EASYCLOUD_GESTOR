package com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces.IClienteEdicion;
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
public class ClienteEdicionRepository implements IClienteEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllCliente EditarAllCliente(RequestEditarAllCliente request) {
        ResponseEditarAllCliente rpt = new ResponseEditarAllCliente();
        String SQL = "{ call CLIENTES.sp_EditarCliente(?,?,?,?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCliente());
            setParameter(pstmt, 2, request.getNombres());
            setParameter(pstmt, 3, request.getApellidos());
            setParameter(pstmt, 4, request.getRazonSocial());
            setParameter(pstmt, 5, request.getNumeroDocumento());
            setParameter(pstmt, 6, request.getIdTipoDocumento());
            setParameter(pstmt, 7, request.getIdTipoCliente());
            setParameter(pstmt, 8, request.getTelefono());
            setParameter(pstmt, 9, request.getEmail());
            setParameter(pstmt, 10, request.getEstado());

            Long userId = 1L;
            pstmt.setLong(11, userId);
            Long empresaId = 1L;
            pstmt.setLong(12, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Cliente actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Cliente.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarCliente", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoCliente EditarEstadoCliente(RequestEditarEstadoCliente request, int estado) {
        ResponseEditarEstadoCliente rpt = new ResponseEditarEstadoCliente();
        String SQL = "{ call CLIENTES.sp_EditarCliente_Estado(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1, request.getIdCliente());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Cliente actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Cliente.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un cliente con ese numero de documento.");
            } else {
                rpt.setMessage("Error al actualizar el cliente.");
            }
            log.error("Error en CLIENTES.sp_EditarCliente_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
