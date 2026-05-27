package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestEditarAllAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestEditarEstadoAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseEditarAllAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseEditarEstadoAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.domain.interfaces.IAperturaCajaEdicion;
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
public class AperturaCajaEdicionRepository implements IAperturaCajaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllAperturaCaja EditarAllAperturaCaja(RequestEditarAllAperturaCaja request) {
        ResponseEditarAllAperturaCaja rpt = new ResponseEditarAllAperturaCaja();
        String SQL = "{ call CAJA.sp_EditarAperturaCaja(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("AperturaCaja actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó AperturaCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_EditarAperturaCaja", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoAperturaCaja EditarEstadoAperturaCaja(RequestEditarEstadoAperturaCaja request, int estado) {
        ResponseEditarEstadoAperturaCaja rpt = new ResponseEditarEstadoAperturaCaja();
        String SQL = "{ call CAJA.sp_EditarAperturaCaja_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdAperturaCaja());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("AperturaCaja actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó AperturaCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_EditarAperturaCaja_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
