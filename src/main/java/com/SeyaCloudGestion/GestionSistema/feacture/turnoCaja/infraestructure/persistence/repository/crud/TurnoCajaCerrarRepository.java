package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestCerrarTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseCerrarTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces.ITurnoCajaCerrar;
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
public class TurnoCajaCerrarRepository implements ITurnoCajaCerrar {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseCerrarTurnoCaja CerrarTurnoCaja(RequestCerrarTurnoCaja request,double montoSistema,double diferencia) {
        ResponseCerrarTurnoCaja rpt = new ResponseCerrarTurnoCaja();
        String SQL = "{ call VENTAS.sp_CerrarTurnoCaja(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            setParameter(pstmt, 1, request.getIdTurnoCaja());
            setParameter(pstmt, 2, montoSistema);
            setParameter(pstmt, 3, request.getMontoReal());
            Long userId = 1L;
            pstmt.setLong(4, userId);
            setParameter(pstmt, 5, diferencia);
            Long empresaId = 1L;
            pstmt.setLong(6, empresaId);

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
            log.error("Error en CAJA.sp_CerrarTurnoCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
