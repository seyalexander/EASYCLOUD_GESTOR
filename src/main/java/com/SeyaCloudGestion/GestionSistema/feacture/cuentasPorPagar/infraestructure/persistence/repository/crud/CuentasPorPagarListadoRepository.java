package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model.EstadoCuenta;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestListaCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseListaCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces.ICuentasPorPagarListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class CuentasPorPagarListadoRepository implements ICuentasPorPagarListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaCuentasPorPagar listaCuentasPorPagar(RequestListaCuentasPorPagar request) {
        ResponseListaCuentasPorPagar rpt = new ResponseListaCuentasPorPagar();
        List<CuentasPorPagarModel> registros = new ArrayList<>();
        String SQL = "{ call COMPRAS.sp_ListarCuentaPorPagar(?,?,?) }";

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
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setCuentasPorPagar(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ListarCuentaPorPagar", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
