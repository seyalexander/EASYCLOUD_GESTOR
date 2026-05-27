package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.request.RequestDetalleCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response.ResponseDetalleCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.domain.interfaces.ICompraDetalleDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.model.CompraDetalleModel;
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
public class CompraDetalleDetalleRepository implements ICompraDetalleDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleCompraDetalle DetalleCompraDetalle(RequestDetalleCompraDetalle request) {
        ResponseDetalleCompraDetalle response = new ResponseDetalleCompraDetalle();
        String SQL = "{ call COMPRAS.sp_ObtenerCompraDetallePorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CompraDetalleModel item = new CompraDetalleModel();
                    item.setIdCompraDetalle(rs.getLong("idCompraDetalle"));
                    item.setIdCompra(rs.getLong("idCompra"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setCantidad(rs.getDouble("cantidad"));
                    item.setCostoUnitario(rs.getDouble("costoUnitario"));
                    item.setTotal(rs.getDouble("total"));
                    item.setFechaIngreso(rs.getString("fechaIngreso"));
                    response.setExito(true);
                    response.setMessage("CompraDetalle obtenido correctamente.");
                    response.setCompraDetalle(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró CompraDetalle.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ObtenerCompraDetallePorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
