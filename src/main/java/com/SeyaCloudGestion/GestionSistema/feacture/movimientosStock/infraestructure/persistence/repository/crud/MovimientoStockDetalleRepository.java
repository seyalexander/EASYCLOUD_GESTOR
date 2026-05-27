package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestDetalleMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseDetalleMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces.IMovimientoStockDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.model.MovimientoStockModel;
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

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class MovimientoStockDetalleRepository implements IMovimientoStockDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleMovimientoStock DetalleMovimientoStock(RequestDetalleMovimientoStock request) {
        ResponseDetalleMovimientoStock response = new ResponseDetalleMovimientoStock();
        String SQL = "{ call ALMACEN.sp_ObtenerMovimientoStockPorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    MovimientoStockModel item = new MovimientoStockModel();
                    item.setIdMovimientoStock(rs.getLong("idMovimientoStock"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setIdAlmacen(rs.getLong("idAlmacen"));
                    item.setIdTipoMovimiento(rs.getLong("idTipoMovimiento"));
                    item.setCantidad(rs.getDouble("cantidad"));
                    item.setCostoUnitario(rs.getDouble("costoUnitario"));
                    item.setObservacion(rs.getString("observacion"));
                    item.setFechaMovimiento((rs.getTimestamp("fechaMovimiento") != null ? rs.getTimestamp("fechaMovimiento").toLocalDateTime() : null));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("MovimientoStock obtenido correctamente.");
                    response.setMovimientoStock(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró MovimientoStock.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ObtenerMovimientoStockPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
