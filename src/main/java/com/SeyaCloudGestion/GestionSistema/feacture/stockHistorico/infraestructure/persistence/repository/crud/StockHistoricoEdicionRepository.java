package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request.RequestEditarAllStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request.RequestEditarEstadoStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response.ResponseEditarAllStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response.ResponseEditarEstadoStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.domain.interfaces.IStockHistoricoEdicion;
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
public class StockHistoricoEdicionRepository implements IStockHistoricoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllStockHistorico EditarAllStockHistorico(RequestEditarAllStockHistorico request) {
        ResponseEditarAllStockHistorico rpt = new ResponseEditarAllStockHistorico();
        String SQL = "{ call ALMACEN.sp_EditarStockHistorico(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("StockHistorico actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó StockHistorico.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarStockHistorico", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoStockHistorico EditarEstadoStockHistorico(RequestEditarEstadoStockHistorico request, int estado) {
        ResponseEditarEstadoStockHistorico rpt = new ResponseEditarEstadoStockHistorico();
        String SQL = "{ call ALMACEN.sp_EditarStockHistorico_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("StockHistorico actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó StockHistorico.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarStockHistorico_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
