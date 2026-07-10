package com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces.IVentaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.CondicionPago;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.VentaModel;
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
public class VentaDetalleRepository implements IVentaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleVenta DetalleVenta(RequestDetalleVenta request) {
        ResponseDetalleVenta response = new ResponseDetalleVenta();
        String SQL = "{ call VENTAS.sp_ObtenerVentaPorId(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            Long empresaId = 1L;
            Long sucursalId = 1L;

            setParameter(pstmt, 1, request.getIdVenta());
            pstmt.setLong(2, sucursalId);
            pstmt.setLong(3, empresaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    VentaModel item = new VentaModel();
                    item.setIdVenta(rs.getLong("idVenta"));
                    item.setIdCliente(rs.getLong("idCliente"));
                    item.setIdUsuario(rs.getLong("idUsuario"));
                    item.setIdSucursal(rs.getLong("idSucursal"));
                    item.setIdTurnoCaja(rs.getLong("idTurnoCaja"));
                    item.setCondicionPago(
                            CondicionPago.valueOf(rs.getString("condicionPago"))
                    );
                    item.setFechaVenta(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );
                    item.setSubTotal(rs.getDouble("subTotal"));
                    item.setImpuesto(rs.getDouble("impuesto"));
                    item.setTotal(rs.getDouble("total"));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );


                    response.setExito(true);
                    response.setMessage("Venta obtenido correctamente.");
                    response.setVenta(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Venta.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerVentaPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
