package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestDetalleProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseDetalleProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.interfaces.IProductoPrecioDetalle;
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

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ProductoPrecioDetalleRepository implements IProductoPrecioDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleProductoPrecio DetalleProductoPrecio(RequestDetalleProductoPrecio request) {
        ResponseDetalleProductoPrecio response = new ResponseDetalleProductoPrecio();
        String SQL = "{ call PRODUCTOS.sp_ObtenerProductoPrecioPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1,request.getIdProductoPrecio());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProductoPrecioModel item = new ProductoPrecioModel();
                    item.setIdProductoPrecio(rs.getLong("idProductoPrecio"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setIdListaPrecio(rs.getLong("idListaPrecio"));
                    item.setPrecio(rs.getDouble("precio"));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    item.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));
                    response.setExito(true);
                    response.setMessage("ProductoPrecio obtenido correctamente.");
                    response.setProductoPrecio(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró ProductoPrecio.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ObtenerProductoPrecioPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
