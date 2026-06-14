package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarEstadoTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseEditarEstadoTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces.ITipoClientesEdicion;
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
public class TipoClientesEdicionRepository implements ITipoClientesEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllTipoClientes EditarAllTipoClientes(RequestEditarAllTipoClientes request) {
        ResponseEditarAllTipoClientes rpt = new ResponseEditarAllTipoClientes();
        String SQL = "{ call CLIENTES.sp_EditarTipoCliente(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //Long userId = 1L;
            //pstmt.setLong(1, userId);

            pstmt.setLong(1, request.getIdTipoCliente());
            pstmt.setString(2, request.getDescripcion());
            pstmt.setInt(3, request.getEstado());
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoClientes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó TipoClientes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un tipo cliente con esa descripción.");
            } else {
                rpt.setMessage("Error al actualizar el tipo cliente.");
            }
            log.error("Error en CONFIGURACION.sp_EditarTipoClientes", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoTipoClientes EditarEstadoTipoClientes(RequestEditarEstadoTipoClientes request, int estado) {
        ResponseEditarEstadoTipoClientes rpt = new ResponseEditarEstadoTipoClientes();
        String SQL = "{ call CLIENTES.sp_EditarTipoCliente_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTipoClientes());
            pstmt.setInt(2, estado);
            Long empresaId = 1L;
            pstmt.setLong(3, empresaId);
            /*
           Long userId = 1L;
            pstmt.setLong(3, userId);
                */
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoClientes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó TipoClientes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_EditarTipoClientes_Estado", e);
        }
        return rpt;
    }

}
