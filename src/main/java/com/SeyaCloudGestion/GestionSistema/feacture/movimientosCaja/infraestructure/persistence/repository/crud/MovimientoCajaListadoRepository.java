package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestListaMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseListaMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.interfaces.IMovimientoCajaListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class MovimientoCajaListadoRepository implements IMovimientoCajaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaMovimientoCaja listaMovimientoCaja(RequestListaMovimientoCaja request) {
        ResponseListaMovimientoCaja rpt = new ResponseListaMovimientoCaja();
        List<MovimientoCajaModel> registros = new ArrayList<>();
        String SQL = "{ call CAJA.sp_ListarMovimientoCaja(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MovimientoCajaModel item = new MovimientoCajaModel();
                item.setIdMovimientoCaja(rs.getLong("idMovimientoCaja"));
                item.setIdAperturaCaja(rs.getLong("idAperturaCaja"));
                item.setMovimiento(Movimiento.valueOf(rs.getString("movimiento")));
                item.setConcepto(rs.getString("concepto"));
                item.setMonto(rs.getDouble("monto"));
                item.setFecha((rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setMovimientoCajas(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ListarMovimientoCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
