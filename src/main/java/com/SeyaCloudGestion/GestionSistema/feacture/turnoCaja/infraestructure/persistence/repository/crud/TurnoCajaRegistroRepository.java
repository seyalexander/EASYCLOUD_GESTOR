package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestRegistroTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseRegistroTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces.ITurnoCajaRegistro;
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
public class TurnoCajaRegistroRepository implements ITurnoCajaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTurnoCaja RegistroTurnoCaja(RequestRegistroTurnoCaja request) {
        ResponseRegistroTurnoCaja rpt = new ResponseRegistroTurnoCaja();
        String SQL = "{ call CAJA.sp_RegistroTurnoCaja(?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdUsuario());
            setParameter(pstmt, 2, request.getIdSucursal());
            setParameter(pstmt, 3, request.getFechaApertura());
            setParameter(pstmt, 4, request.getFechaCierre());
            setParameter(pstmt, 5, request.getMontoInicial());
            setParameter(pstmt, 6, request.getMontoFinal());
            setParameter(pstmt, 7, request.getEstado());
            Long userId = 1L;
            pstmt.setLong(8, userId);

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
            log.error("Error en CAJA.sp_RegistroTurnoCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
