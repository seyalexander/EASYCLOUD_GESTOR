package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestDetalleNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseDetalleNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces.INotaCreditoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.model.NotaCreditoModel;
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
public class NotaCreditoDetalleRepository implements INotaCreditoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleNotaCredito DetalleNotaCredito(RequestDetalleNotaCredito request) {
        ResponseDetalleNotaCredito response = new ResponseDetalleNotaCredito();
        String SQL = "{ call VENTAS.sp_ObtenerNotaCreditoPorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    NotaCreditoModel item = new NotaCreditoModel();
                    item.setIdNotaCredito(rs.getLong("idNotaCredito"));
                    item.setIdVenta(rs.getLong("idVenta"));
                    item.setMotivo(rs.getString("motivo"));
                    item.setFechaEmision((rs.getTimestamp("fechaEmision") != null ? rs.getTimestamp("fechaEmision").toLocalDateTime() : null));
                    item.setTotal(rs.getDouble("total"));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("NotaCredito obtenido correctamente.");
                    response.setNotaCredito(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró NotaCredito.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerNotaCreditoPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
