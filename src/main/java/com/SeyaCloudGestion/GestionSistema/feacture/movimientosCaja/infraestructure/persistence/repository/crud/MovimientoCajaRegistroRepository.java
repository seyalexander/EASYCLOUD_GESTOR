package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.interfaces.IMovimientoCajaRegistro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class MovimientoCajaRegistroRepository implements IMovimientoCajaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroMovimientoCaja RegistroMovimientoCaja(RequestRegistroMovimientoCaja request) {
        ResponseRegistroMovimientoCaja rpt = new ResponseRegistroMovimientoCaja();
        String SQL = "{ call CAJA.sp_RegistrarMovimientoCaja(?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdTurnoCaja());
            String tipoMovimiento = (request.getMovimiento() != null) ? request.getMovimiento().name() : null;
            setParameter(pstmt, 2, tipoMovimiento);
            setParameter(pstmt, 3, request.getConcepto());
            setParameter(pstmt, 4, request.getMonto());
            Long empresaId = 1L;
            Long sucursalId = 1L;
            Long userId = 1L;

            pstmt.setLong(5, userId);
            pstmt.setLong(6, empresaId);
            pstmt.setLong(7, sucursalId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Movimiento insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó MovimientoCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_RegistrarMovimientoCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
