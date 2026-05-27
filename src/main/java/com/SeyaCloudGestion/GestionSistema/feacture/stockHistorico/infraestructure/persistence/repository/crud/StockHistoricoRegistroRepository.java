package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request.RequestRegistroStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.response.ResponseRegistroStockHistorico;
import com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.domain.interfaces.IStockHistoricoRegistro;
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
public class StockHistoricoRegistroRepository implements IStockHistoricoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroStockHistorico RegistroStockHistorico(RequestRegistroStockHistorico request) {
        ResponseRegistroStockHistorico rpt = new ResponseRegistroStockHistorico();
        String SQL = "{ call ALMACEN.sp_RegistroStockHistorico(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdArticulo());
            setParameter(pstmt, 2, request.getIdAlmacen());
            setParameter(pstmt, 3, request.getStock());
            setParameter(pstmt, 4, request.getFecha());
            Long userId = 1L;
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("StockHistorico insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó StockHistorico.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_RegistroStockHistorico", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
