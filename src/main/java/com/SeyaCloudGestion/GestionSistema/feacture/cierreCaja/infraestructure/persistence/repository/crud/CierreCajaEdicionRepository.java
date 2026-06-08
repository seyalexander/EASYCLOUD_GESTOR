package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestEditarAllCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestEditarEstadoCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseEditarAllCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseEditarEstadoCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.domain.interfaces.ICierreCajaEdicion;
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
public class CierreCajaEdicionRepository implements ICierreCajaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllCierreCaja EditarAllCierreCaja(RequestEditarAllCierreCaja request) {
        ResponseEditarAllCierreCaja rpt = new ResponseEditarAllCierreCaja();
        String SQL = "{ call CAJA.sp_EditarCierreCaja(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CierreCaja actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó CierreCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_EditarCierreCaja", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoCierreCaja EditarEstadoCierreCaja(RequestEditarEstadoCierreCaja request, int estado) {
        ResponseEditarEstadoCierreCaja rpt = new ResponseEditarEstadoCierreCaja();
        String SQL = "{ call CAJA.sp_EditarCierreCaja_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCierreCaja());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CierreCaja actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó CierreCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_EditarCierreCaja_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
