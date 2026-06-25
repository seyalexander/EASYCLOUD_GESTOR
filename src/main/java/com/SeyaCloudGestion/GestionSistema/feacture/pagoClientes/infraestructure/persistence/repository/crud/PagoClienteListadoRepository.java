package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestListaPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseListaPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces.IPagoClienteListado;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.model.PagoClienteModel;
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
public class PagoClienteListadoRepository implements IPagoClienteListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaPagoCliente listaPagoCliente(RequestListaPagoCliente request) {
        ResponseListaPagoCliente rpt = new ResponseListaPagoCliente();
        List<PagoClienteModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarPagoCliente() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PagoClienteModel item = new PagoClienteModel();
                item.setIdPagoCliente(rs.getLong("idPagoCliente"));
                item.setIdCuentaPorCobrar(rs.getLong("idCuentaPorCobrar"));
                item.setFechaPago((rs.getTimestamp("fechaPago") != null ? rs.getTimestamp("fechaPago").toLocalDateTime() : null));
                item.setMontoPagado(rs.getDouble("montoPagado"));
                item.setIdTipoPago(rs.getLong("idTipoPago"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setPagoClientes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarPagoCliente", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
