package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestEditarAllTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestEditarEstadoTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseEditarAllTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseEditarEstadoTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces.ITurnoCajaEdicion;
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
public class TurnoCajaEdicionRepository implements ITurnoCajaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllTurnoCaja EditarAllTurnoCaja(RequestEditarAllTurnoCaja request) {
        ResponseEditarAllTurnoCaja rpt = new ResponseEditarAllTurnoCaja();
        String SQL = "{ call VENTAS.sp_EditarTurnoCaja(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            setParameter(pstmt, 1, request.getIdTurnoCaja());
            setParameter(pstmt, 2, request.getFechaCierre());
            setParameter(pstmt, 3, request.getMontoFinal());
            setParameter(pstmt, 4, request.getEstado());
            Long userId = 1L;
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TurnoCaja actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó TurnoCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_EditarTurnoCaja", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoTurnoCaja EditarEstadoTurnoCaja(RequestEditarEstadoTurnoCaja request, int estado) {
        ResponseEditarEstadoTurnoCaja rpt = new ResponseEditarEstadoTurnoCaja();
        String SQL = "{ call CAJA.sp_EditarTurnoCaja_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdTurnoCaja());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TurnoCaja actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó TurnoCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_EditarTurnoCaja_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
