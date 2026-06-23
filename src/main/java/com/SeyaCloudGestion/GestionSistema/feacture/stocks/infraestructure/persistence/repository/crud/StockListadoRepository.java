package com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestListaSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseListaSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces.ISotckListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class StockListadoRepository implements ISotckListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaSotck listaSotck(RequestListaSotck request) {
        ResponseListaSotck rpt = new ResponseListaSotck();
        List<SotckModel> registros = new ArrayList<>();

        String SQL = "{ call INVENTARIO.sp_ListarStockProducto(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId = 1L;
            pstmt.setLong(1, empresaId);
            Long sucursaleId = 1L;
            pstmt.setLong(2, sucursaleId);
            pstmt.setLong(3, request.getIdProducto());
            pstmt.setLong(4, request.getIdAlmacen());

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    SotckModel item = new SotckModel();
                    item.setIdStock(rs.getLong("idStockProducto"));
                    item.setStock(rs.getDouble("stock"));

                    item.setIdProducto(rs.getLong("idProducto"));
                    item.setDescripcionProducto(rs.getString("descripcionProducto"));

                    item.setIdAlmacen(rs.getLong("idAlmacen"));
                    item.setDescripcionAlmacen(rs.getString("descripcionAlmacen"));

                    item.setIdSucursal(rs.getLong("idSucursal"));
                    item.setDescripcionSucursal(rs.getString("descripcionSucursal"));

                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setSotcks(registros);
            rpt.setMessage("Consulta de stock realizada correctamente.");

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ListarStock", e);
        }
        return rpt;
    }
}