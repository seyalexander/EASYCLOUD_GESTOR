package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestDetalleProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseDetalleProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.interfaces.IProductoImpuestoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.model.ProductoImpuestoModel;
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
public class ProductoImpuestoDetalleRepository implements IProductoImpuestoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleProductoImpuesto DetalleProductoImpuesto(RequestDetalleProductoImpuesto request) {
        ResponseDetalleProductoImpuesto response = new ResponseDetalleProductoImpuesto();
        String SQL = "{ call PRODUCTOS.sp_ObtenerProductoImpuestoPorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProductoImpuestoModel item = new ProductoImpuestoModel();
                    item.setIdProductoImpuesto(rs.getLong("idProductoImpuesto"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setPorcentaje(rs.getDouble("porcentaje"));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("ProductoImpuesto obtenido correctamente.");
                    response.setProductoImpuesto(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró ProductoImpuesto.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ObtenerProductoImpuestoPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
