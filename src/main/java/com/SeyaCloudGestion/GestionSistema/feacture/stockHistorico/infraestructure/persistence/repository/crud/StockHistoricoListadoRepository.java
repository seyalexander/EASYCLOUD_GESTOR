package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request.RequestListaStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response.ResponseListaStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.domain.interfaces.IStockHistoricoListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class StockHistoricoListadoRepository implements IStockHistoricoListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaStockHistorico listaStockHistorico(RequestListaStockHistorico request) {
        ResponseListaStockHistorico rpt = new ResponseListaStockHistorico();
        List<StockHistoricoModel> registros = new ArrayList<>();
        String SQL = "{ call ALMACEN.sp_ListarStockHistorico() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StockHistoricoModel item = new StockHistoricoModel();
                item.setIdStockHistorico(rs.getLong("idStockHistorico"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setIdAlmacen(rs.getLong("idAlmacen"));
                item.setStock(rs.getDouble("stock"));
                item.setFecha((rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setStockHistoricos(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarStockHistorico", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
