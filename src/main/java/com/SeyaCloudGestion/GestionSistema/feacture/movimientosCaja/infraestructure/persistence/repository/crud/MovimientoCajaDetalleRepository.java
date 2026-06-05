package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestDetalleMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseDetalleMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.interfaces.IMovimientoCajaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.Movimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.MovimientoCajaModel;
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
public class MovimientoCajaDetalleRepository implements IMovimientoCajaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleMovimientoCaja DetalleMovimientoCaja(RequestDetalleMovimientoCaja request) {
        ResponseDetalleMovimientoCaja response = new ResponseDetalleMovimientoCaja();
        String SQL = "{ call CAJA.sp_ObtenerMovimientoCajaPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdMovimientoCaja());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    MovimientoCajaModel item = new MovimientoCajaModel();
                    item.setIdMovimientoCaja(rs.getLong("idMovimientoCaja"));
                    item.setIdAperturaCaja(rs.getLong("idAperturaCaja"));
                    String movimiento = rs.getString("movimiento");
                    if (movimiento != null) {
                        item.setMovimiento(Movimiento.valueOf(movimiento.toUpperCase()));
                    }
                    item.setConcepto(rs.getString("concepto"));
                    item.setMonto(rs.getDouble("monto"));
                    item.setFecha((rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("MovimientoCaja obtenido correctamente.");
                    response.setMovimientoCaja(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró MovimientoCaja.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ObtenerMovimientoCajaPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
