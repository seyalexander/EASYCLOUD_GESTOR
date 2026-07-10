package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestListaCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestListaCuentasPorCobrarIDCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseListaCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.interfaces.ICuentasPorCobrarListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class CuentasPorCobrarListadoRepository implements ICuentasPorCobrarListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaCuentasPorCobrar ListaCuentasPorCobrar(RequestListaCuentasPorCobrar request) {
        ResponseListaCuentasPorCobrar rpt = new ResponseListaCuentasPorCobrar();
        List<CuentasPorCobrarModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarCuentaPorCobrar(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            String estadoString = (request.getEstado() != null) ? request.getEstado().name() : "TODOS";
            setParameter(pstmt, 1, estadoString);
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);
            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
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
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setCuentasPorCobrares(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarCuentaPorCobrar", e);
        }
        return rpt;
    }

    @Override
    public ResponseListaCuentasPorCobrar ListaCuentasPorCobrarIDCliente(RequestListaCuentasPorCobrarIDCliente request) {
        ResponseListaCuentasPorCobrar rpt = new ResponseListaCuentasPorCobrar();
        List<CuentasPorCobrarModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarCuentaPorCobrarPorCliente(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            setParameter(pstmt, 1, request.getIdCliente());
            String estadoString = (request.getEstado() != null) ? request.getEstado().name() : "TODOS";
            setParameter(pstmt, 2, estadoString);
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(3, empresaId);
            pstmt.setLong(4, sucursalId);

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
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
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setCuentasPorCobrares(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarCuentaPorCobrarPorCliente", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
