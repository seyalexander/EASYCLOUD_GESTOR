package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestEditarAllMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestEditarEstadoMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseEditarAllMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseEditarEstadoMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces.IMovimientoStockEdicion;
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
public class MovimientoStockEdicionRepository implements IMovimientoStockEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllMovimientoStock EditarAllMovimientoStock(RequestEditarAllMovimientoStock request) {
        ResponseEditarAllMovimientoStock rpt = new ResponseEditarAllMovimientoStock();
        String SQL = "{ call ALMACEN.sp_EditarMovimientoStock(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("MovimientoStock actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó MovimientoStock.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarMovimientoStock", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoMovimientoStock EditarEstadoMovimientoStock(RequestEditarEstadoMovimientoStock request, int estado) {
        ResponseEditarEstadoMovimientoStock rpt = new ResponseEditarEstadoMovimientoStock();
        String SQL = "{ call ALMACEN.sp_EditarMovimientoStock_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("MovimientoStock actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó MovimientoStock.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarMovimientoStock_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
