package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestEditarAllTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestEditarEstadoTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseEditarAllTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseEditarEstadoTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.interfaces.ITipoMovimientoEdicion;
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
public class TipoMovimientoEdicionRepository implements ITipoMovimientoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllTipoMovimiento EditarAllTipoMovimiento(RequestEditarAllTipoMovimiento request) {
        ResponseEditarAllTipoMovimiento rpt = new ResponseEditarAllTipoMovimiento();
        String SQL = "{ call INVENTARIO.sp_EditarTipoMovimiento(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1, request.getIdTipoMovimiento());
            setParameter(pstmt, 2, request.getDescripcion());
            setParameter(pstmt, 3, request.getEsEntrada());
            setParameter(pstmt, 4, request.getEstado());
            Long userId = 1L;
            pstmt.setLong(5, userId);
            Long empresaId = 1L;
            pstmt.setLong(6, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoMovimiento actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó TipoMovimiento.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un tipo de movimiento con esa descripción.");
            } else {
                rpt.setMessage("Error al registrar el tipo movimiento.");
            }
            log.error("Error en INVENTARIO.sp_EditarTipoMovimiento", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoTipoMovimiento EditarEstadoTipoMovimiento(RequestEditarEstadoTipoMovimiento request, int estado) {
        ResponseEditarEstadoTipoMovimiento rpt = new ResponseEditarEstadoTipoMovimiento();
        String SQL = "{ call INVENTARIO.sp_EditarTipoMovimiento_Estado(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTipoMovimiento());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoMovimiento actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó TipoMovimiento.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarTipoMovimiento_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
