package com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces.ICompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.model.CompraModel;
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
public class CompraDetalleRepository implements ICompraDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleCompra DetalleCompra(RequestDetalleCompra request) {
        ResponseDetalleCompra response = new ResponseDetalleCompra();
        String SQL = "{ call COMPRAS.sp_ObtenerCompraPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdCompra());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);
            Long sucursalId = 1L;
            pstmt.setLong(3, sucursalId);

            ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    CompraModel item = new CompraModel();
                    item.setIdCompra(rs.getLong("idCompra"));
                    item.setIdProveedor(rs.getLong("idProveedor"));
                    item.setIdSucursal(rs.getLong("idSucursal"));
                    item.setIdAlmacen(rs.getLong("idAlmacen"));
                    item.setIdTipoComprobante(rs.getLong("idTipoComprobante"));
                    item.setSerie(rs.getString("serieComprobante"));
                    item.setNumero(rs.getString("numeroComprobante"));
                    item.setFechaCompra(rs.getString("fechaCompra"));
                    item.setSubTotal(rs.getDouble("subTotal"));
                    item.setImpuesto(rs.getDouble("impuesto"));
                    item.setTotal(rs.getDouble("total"));
                    item.setEstado(rs.getInt("estado"));

                    response.setExito(true);
                    response.setMessage("Compra obtenido correctamente.");
                    response.setCompra(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Compra.");
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ObtenerCompraPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
