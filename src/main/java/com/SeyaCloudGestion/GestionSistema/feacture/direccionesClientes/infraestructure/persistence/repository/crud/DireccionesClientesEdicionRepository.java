package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestEditarAllDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestEditarEstadoDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseEditarAllDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseEditarEstadoDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.interfaces.IDireccionesClientesEdicion;
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
public class DireccionesClientesEdicionRepository implements IDireccionesClientesEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllDireccionesClientes EditarAllDireccionesClientes(RequestEditarAllDireccionesClientes request) {
        ResponseEditarAllDireccionesClientes rpt = new ResponseEditarAllDireccionesClientes();
        String SQL = "{ call CLIENTES.sp_EditarDireccionCliente(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1, request.getIdDireccionCliente());
            setParameter(pstmt, 2, request.getIdCliente());
            setParameter(pstmt, 3, request.getDireccion());
            setParameter(pstmt, 4, request.getDepartamento());
            setParameter(pstmt, 5, request.getProvincia());
            setParameter(pstmt, 6, request.getDistrito());
            setParameter(pstmt, 7, request.getReferencia());
            Long userId = 1L;
            pstmt.setLong(8, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("DireccionesClientes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó DireccionesClientes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarDireccionesClientes", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoDireccionesClientes EditarEstadoDireccionesClientes(RequestEditarEstadoDireccionesClientes request, int estado) {
        ResponseEditarEstadoDireccionesClientes rpt = new ResponseEditarEstadoDireccionesClientes();
        String SQL = "{ call CLIENTES.sp_EditarDireccionCliente_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdDireccionesClientes());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("DireccionesClientes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó DireccionesClientes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CLIENTES.sp_EditarDireccionCliente_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
