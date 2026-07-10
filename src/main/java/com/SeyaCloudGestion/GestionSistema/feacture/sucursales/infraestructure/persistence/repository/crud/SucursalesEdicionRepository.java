package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarEstadoSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseEditarEstadoSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces.ISucursalesEdicion;
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
public class SucursalesEdicionRepository implements ISucursalesEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllSucursales EditarAllSucursales(RequestEditarAllSucursales request) {
        ResponseEditarAllSucursales rpt = new ResponseEditarAllSucursales();
        String SQL = "{ call INVENTARIO.sp_EditarSucursales(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdSucursales());
            pstmt.setString(2, request.getDescripcion());
            pstmt.setInt(3, request.getEstado());
            Long userId = 1L;
            pstmt.setLong(4, userId);
            Long empresaId = 1L;
            pstmt.setLong(5, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Sucursales actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Sucursales.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe una sucursal con esa descripción.");
            } else {
                rpt.setMessage("Error al actualizar la sucursal.");
            }
            log.error("Error en INVENTARIO.sp_EditarSucursales", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoSucursales EditarEstadoSucursales(RequestEditarEstadoSucursales request, int estado) {
        ResponseEditarEstadoSucursales rpt = new ResponseEditarEstadoSucursales();
        String SQL = "{ call INVENTARIO.sp_EditarSucursales_Estado(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdSucursales());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Sucursales actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Sucursales.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_EditarSucursales_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
