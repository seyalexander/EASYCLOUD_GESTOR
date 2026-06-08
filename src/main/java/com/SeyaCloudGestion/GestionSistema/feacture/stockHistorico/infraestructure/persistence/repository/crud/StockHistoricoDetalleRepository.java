package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request.RequestDetalleStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response.ResponseDetalleStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.domain.interfaces.IStockHistoricoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.model.StockHistoricoModel;
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
public class StockHistoricoDetalleRepository implements IStockHistoricoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleStockHistorico DetalleStockHistorico(RequestDetalleStockHistorico request) {
        ResponseDetalleStockHistorico response = new ResponseDetalleStockHistorico();
        String SQL = "{ call ALMACEN.sp_ObtenerStockHistoricoPorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    StockHistoricoModel item = new StockHistoricoModel();
                    item.setIdStockHistorico(rs.getLong("idStockHistorico"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setIdAlmacen(rs.getLong("idAlmacen"));
                    item.setStock(rs.getDouble("stock"));
                    item.setFecha((rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("StockHistorico obtenido correctamente.");
                    response.setStockHistorico(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró StockHistorico.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ObtenerStockHistoricoPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
