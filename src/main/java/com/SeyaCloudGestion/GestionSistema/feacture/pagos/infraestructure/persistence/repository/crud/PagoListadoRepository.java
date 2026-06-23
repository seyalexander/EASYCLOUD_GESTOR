package com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces.IPagoListado;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.model.PagoModel;
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
public class PagoListadoRepository implements IPagoListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaPago listaPago(RequestListaPago request) {
        ResponseListaPago rpt = new ResponseListaPago();
        List<PagoModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarPago(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {


            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    PagoModel item = new PagoModel();
                item.setIdPago(rs.getLong("idPago"));
                item.setIdVenta(rs.getLong("idVenta"));
                item.setIdTipoPago(rs.getLong("idTipoPago"));
                item.setMonto(rs.getDouble("monto"));
                item.setReferencia(rs.getString("referencia"));
                item.setFechaPago((rs.getTimestamp("fechaPago") != null ? rs.getTimestamp("fechaPago").toLocalDateTime() : null));
                item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setPagos(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarPago", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
