package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestEditarAllProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestEditarEstadoProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseEditarAllProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseEditarEstadoProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.interfaces.IProductoImpuestoEdicion;
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
public class ProductoImpuestoEdicionRepository implements IProductoImpuestoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllProductoImpuesto EditarAllProductoImpuesto(RequestEditarAllProductoImpuesto request) {
        ResponseEditarAllProductoImpuesto rpt = new ResponseEditarAllProductoImpuesto();
        String SQL = "{ call PRODUCTOS.sp_EditarProductoImpuesto(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ProductoImpuesto actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó ProductoImpuesto.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarProductoImpuesto", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoProductoImpuesto EditarEstadoProductoImpuesto(RequestEditarEstadoProductoImpuesto request, int estado) {
        ResponseEditarEstadoProductoImpuesto rpt = new ResponseEditarEstadoProductoImpuesto();
        String SQL = "{ call PRODUCTOS.sp_EditarProductoImpuesto_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ProductoImpuesto actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó ProductoImpuesto.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarProductoImpuesto_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
