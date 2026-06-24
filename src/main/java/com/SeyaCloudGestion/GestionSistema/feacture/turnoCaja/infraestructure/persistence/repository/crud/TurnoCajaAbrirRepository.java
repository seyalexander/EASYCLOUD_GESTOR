package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestAbrirTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseAbrirTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces.ITurnoCajaAbrir;
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
public class TurnoCajaAbrirRepository implements ITurnoCajaAbrir {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseAbrirTurnoCaja AbrirTurnoCaja(RequestAbrirTurnoCaja request) {
        ResponseAbrirTurnoCaja rpt = new ResponseAbrirTurnoCaja();
        String SQL = "{ call VENTAS.sp_AbrirTurnoCaja(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            Long userId = 1L;
            pstmt.setLong(1, userId);
            Long sucursalId = 1L;
            setParameter(pstmt, 2, sucursalId);
            setParameter(pstmt, 3, request.getMontoInicial());
            setParameter(pstmt, 4, request.getMontoInicial());
            Long empresaId = 1L;
            pstmt.setLong(5, empresaId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TurnoCaja insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó TurnoCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_AbrirTurnoCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
