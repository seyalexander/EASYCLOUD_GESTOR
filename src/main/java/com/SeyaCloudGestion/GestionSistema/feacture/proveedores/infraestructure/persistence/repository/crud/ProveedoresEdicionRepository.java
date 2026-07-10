package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarAllProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarEstadoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarAllProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarEstadoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresEdicion;
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
public class ProveedoresEdicionRepository implements IProveedoresEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllProveedor EditarAllProveedores(RequestEditarAllProveedor request) {
        ResponseEditarAllProveedor rpt = new ResponseEditarAllProveedor();
        String SQL = "{ call COMPRAS.sp_EditarProveedor(?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdProveedor());
            pstmt.setString(2, request.getRazonSocial());
            pstmt.setString(3, request.getRuc());
            pstmt.setString(4, request.getTelefono());
            pstmt.setString(5, request.getEmail());
            pstmt.setString(6, request.getDireccion());
            Long empresaId = 1L;
            pstmt.setLong(7, empresaId);
            pstmt.setInt(8, request.getEstado());
            Long userId = 1L;
            pstmt.setLong(9, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Proveedores actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Proveedores.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarProveedores", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoProveedor EditarEstadoProveedores(RequestEditarEstadoProveedor request, int estado) {
        ResponseEditarEstadoProveedor rpt = new ResponseEditarEstadoProveedor();
        String SQL = "{ call COMPRAS.sp_EditarProveedor_Estado(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdProveedor());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Proveedores actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Proveedores.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe este proveedor .");
            } else {
                rpt.setMessage("Error al registrar el proveedor.");
            }
            log.error("Error en COMPRAS.sp_EditarProveedor_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
