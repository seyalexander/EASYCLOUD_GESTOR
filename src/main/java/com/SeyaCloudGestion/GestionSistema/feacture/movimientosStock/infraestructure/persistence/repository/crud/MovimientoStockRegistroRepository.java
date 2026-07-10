package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseRegistroMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces.IMovimientoStockRegistro;
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
public class MovimientoStockRegistroRepository implements IMovimientoStockRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroMovimientoStock RegistroMovimientoStock(RequestRegistroMovimientoStock request) {
        ResponseRegistroMovimientoStock rpt = new ResponseRegistroMovimientoStock();
        String SQL = "{ call INVENTARIO.sp_RegistroMovimientoStock(?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdArticulo());
            setParameter(pstmt, 2, request.getIdAlmacen());
            setParameter(pstmt, 3, request.getIdTipoMovimiento());
            setParameter(pstmt, 4, request.getCantidad());
            setParameter(pstmt, 5, request.getCostoUnitario());
            setParameter(pstmt, 6, request.getObservacion());
            Long userId = 1L;
            pstmt.setLong(7, userId);
            Long empresaId= 1L;
            Long sucursalId= 1L;
            setParameter(pstmt, 8, empresaId);
            setParameter(pstmt, 9, sucursalId);


            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("MovimientoStock insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó MovimientoStock.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_RegistroMovimientoStock", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
