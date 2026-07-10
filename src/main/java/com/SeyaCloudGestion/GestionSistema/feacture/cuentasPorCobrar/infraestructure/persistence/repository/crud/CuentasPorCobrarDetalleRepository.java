package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.interfaces.ICuentasPorCobrarDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.CuentasPorCobrarModel;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
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
public class CuentasPorCobrarDetalleRepository implements ICuentasPorCobrarDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleCuentasPorCobrar DetalleCuentasPorCobrar(RequestDetalleCuentasPorCobrar request) {
        ResponseDetalleCuentasPorCobrar response = new ResponseDetalleCuentasPorCobrar();
        String SQL = "{ call VENTAS.sp_ObtenerCuentaPorCobrarPorId(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCuentasPorCobrar());
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CuentasPorCobrarModel item = new CuentasPorCobrarModel();
                    item.setIdCuentaPorCobrar(rs.getLong("idCuentaPorCobrar"));
                    item.setIdVenta(rs.getLong("idVenta"));
                    item.setMontoPendiente(rs.getDouble("montoPendiente"));
                    item.setFechaVencimiento((rs.getTimestamp("fechaVencimiento") != null ? rs.getTimestamp("fechaVencimiento").toLocalDateTime() : null));
                    String estadoBD = rs.getString("estado");
                    if (estadoBD != null) {
                        item.setEstado(EstadoCuenta.valueOf(estadoBD.toUpperCase().trim()));
                    } else {
                        item.setEstado(null);
                    }

                    response.setExito(true);
                    response.setMessage("CuentasPorCobrar obtenido correctamente.");
                    response.setCuentasPorCobrar(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró CuentasPorCobrar.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerCuentasPorCobrarPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
