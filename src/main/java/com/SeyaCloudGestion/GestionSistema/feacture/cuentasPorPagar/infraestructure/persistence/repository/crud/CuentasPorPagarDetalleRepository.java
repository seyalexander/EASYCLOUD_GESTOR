package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestDetalleCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseDetalleCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces.ICuentasPorPagarDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.model.CuentasPorPagarModel;
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
public class CuentasPorPagarDetalleRepository implements ICuentasPorPagarDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleCuentasPorPagar DetalleCuentasPorPagar(RequestDetalleCuentasPorPagar request) {
        ResponseDetalleCuentasPorPagar response = new ResponseDetalleCuentasPorPagar();
        String SQL = "{ call COMPRAS.sp_ObtenerCuentasPorPagarPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCuentasPorPagar());
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CuentasPorPagarModel item = new CuentasPorPagarModel();
                    item.setIdCuentaPorPagar(rs.getLong("idCuentaPorPagar"));
                    item.setIdCompra(rs.getLong("idCompra"));
                    item.setMontoPendiente(rs.getDouble("montoPendiente"));
                    item.setFechaVencimiento((rs.getTimestamp("fechaVencimiento") != null ? rs.getTimestamp("fechaVencimiento").toLocalDateTime() : null));
                    String estadoBD = rs.getString("estado");
                    if (estadoBD != null) {
                        item.setEstado(EstadoCuenta.valueOf(estadoBD.toUpperCase().trim()));
                    } else {
                        item.setEstado(null);
                    }
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);

                    response.setMessage("CuentasPorPagar obtenido correctamente.");
                    response.setCuentasPorPagar(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró CuentasPorPagar.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en  COMPRAS.sp_ObtenerCuentasPorPagarPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
