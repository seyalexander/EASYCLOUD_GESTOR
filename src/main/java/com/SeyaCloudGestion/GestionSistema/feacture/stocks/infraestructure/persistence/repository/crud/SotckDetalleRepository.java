package com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.repository.crud;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseDetalleSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces.ISotckDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.model.SotckModel;
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
public class SotckDetalleRepository implements ISotckDetalle {
    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleSotck DetalleSotck(RequestDetalleSotck request) {

        ResponseDetalleSotck response = new ResponseDetalleSotck();

        String SQL = "{ call INVENTARIO.sp_ObtenerStockProductoPorId(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdProducto());

            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);
            Long sucursaleId = 1L;
            pstmt.setLong(3, sucursaleId);
            pstmt.setLong(4, request.getIdAlmacen());


            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    SotckModel item = new SotckModel();

                    item.setIdStock(rs.getLong("idStockProducto"));
                    item.setStock(rs.getDouble("stock"));

                    item.setIdProducto(rs.getLong("idProducto"));
                    //item.setDescripcionProducto(rs.getString("descripcionProducto"));

                    item.setIdAlmacen(rs.getLong("idAlmacen"));
                    //item.setDescripcionAlmacen(rs.getString("descripcionAlmacen"));

                    item.setIdSucursal(rs.getLong("idSucursal"));
                    //item.setDescripcionSucursal(rs.getString("descripcionSucursal"));

                    response.setExito(true);
                    response.setMessage("Stock obtenido correctamente.");
                    response.setSotck(item);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Stock.");
                }
            }

        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ObtenerStockProductoPorId", e);
        }

        return response;
    }

}