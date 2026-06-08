package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestListaPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseListaPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces.IPagoProveedoresListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class PagoProveedoresListadoRepository implements IPagoProveedoresListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaPagoProveedores listaPagoProveedores(RequestListaPagoProveedores request) {
        ResponseListaPagoProveedores rpt = new ResponseListaPagoProveedores();
        List<PagoProveedoresModel> registros = new ArrayList<>();
        String SQL = "{ call COMPRAS.sp_ListarPagoProveedores(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PagoProveedoresModel item = new PagoProveedoresModel();
                item.setIdPagoProveedor(rs.getLong("idPagoProveedor"));
                item.setIdCuentaPorPagar(rs.getLong("idCuentaPorPagar"));
                item.setFechaPago((rs.getTimestamp("fechaPago") != null ? rs.getTimestamp("fechaPago").toLocalDateTime() : null));
                item.setMontoPagado(rs.getDouble("montoPagado"));
                item.setMetodoPago(rs.getString("metodoPago"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setPagoProveedores(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ListarPagoProveedores", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
