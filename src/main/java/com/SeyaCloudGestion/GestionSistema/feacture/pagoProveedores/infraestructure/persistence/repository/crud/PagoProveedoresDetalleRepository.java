package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestDetallePagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseDetallePagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces.IPagoProveedoresDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model.PagoProveedoresModel;
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
public class PagoProveedoresDetalleRepository implements IPagoProveedoresDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetallePagoProveedores DetallePagoProveedores(RequestDetallePagoProveedores request) {
        ResponseDetallePagoProveedores response = new ResponseDetallePagoProveedores();
        String SQL = "{ call COMPRAS.sp_ObtenerPagoProveedoresPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdPagoProveedores());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PagoProveedoresModel item = new PagoProveedoresModel();
                    item.setIdPagoProveedor(rs.getLong("idPagoProveedor"));
                    item.setIdCuentaPorPagar(rs.getLong("idCuentaPorPagar"));
                    item.setFechaPago((rs.getTimestamp("fechaPago") != null ? rs.getTimestamp("fechaPago").toLocalDateTime() : null));
                    item.setMontoPagado(rs.getDouble("montoPagado"));
                    item.setMetodoPago(rs.getString("metodoPago"));
                    response.setExito(true);
                    response.setMessage("PagoProveedores obtenido correctamente.");
                    response.setPagoProveedores(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró PagoProveedores.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ObtenerPagoProveedoresPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
