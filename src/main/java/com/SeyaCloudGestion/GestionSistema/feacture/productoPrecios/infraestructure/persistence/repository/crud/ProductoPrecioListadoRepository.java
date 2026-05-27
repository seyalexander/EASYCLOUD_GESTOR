package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestListaProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseListaProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.interfaces.IProductoPrecioListado;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.model.ProductoPrecioModel;
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
public class ProductoPrecioListadoRepository implements IProductoPrecioListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaProductoPrecio listaProductoPrecio(RequestListaProductoPrecio request) {
        ResponseListaProductoPrecio rpt = new ResponseListaProductoPrecio();
        List<ProductoPrecioModel> registros = new ArrayList<>();
        String SQL = "{ call PRODUCTOS.sp_ListarProductoPrecio() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductoPrecioModel item = new ProductoPrecioModel();
                item.setIdProductoPrecio(rs.getLong("idProductoPrecio"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setIdListaPrecio(rs.getLong("idListaPrecio"));
                item.setPrecio(rs.getDouble("precio"));
                item.setFechaInicio(rs.getString("fechaInicio"));
                item.setFechaFin(rs.getString("fechaFin"));
                item.setEstado(rs.getInt("estado"));
                item.setFechaIngreso(rs.getString("fechaIngreso"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setProductoPrecios(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ListarProductoPrecio", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
