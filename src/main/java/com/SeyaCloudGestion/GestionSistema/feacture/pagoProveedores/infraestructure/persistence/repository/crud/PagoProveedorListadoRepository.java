package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestListaPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseListaPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces.IPagoProveedoresListado;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model.PagoProveedorModel;
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
public class PagoProveedorListadoRepository implements IPagoProveedoresListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaPagoProveedor listaPagoProveedor(RequestListaPagoProveedor request) {
        ResponseListaPagoProveedor rpt = new ResponseListaPagoProveedor();
        List<PagoProveedorModel> registros = new ArrayList<>();
        String SQL = "{ call COMPRAS.sp_ListarPagoProveedor(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId = 1L;
            pstmt.setLong(1, empresaId);
            Long sucursalId = 1L;
            pstmt.setLong(2, sucursalId);

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    PagoProveedorModel item = new PagoProveedorModel();
                item.setIdPagoProveedor(rs.getLong("idPagoProveedor"));
                item.setIdCuentaPorPagar(rs.getLong("idCuentaPorPagar"));
                item.setFechaPago((rs.getTimestamp("fechaPago") != null ? rs.getTimestamp("fechaPago").toLocalDateTime() : null));
                item.setMontoPagado(rs.getDouble("montoPagado"));
                item.setIdTipoPago(rs.getLong("idTipoPago"));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setPagoProveedor(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ListarPagoProveedor", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
