package com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestEditarAllCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseEditarAllCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces.ICajaEdicion;
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
public class CajaEdicionRepository implements ICajaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllCaja EditarAllCaja(RequestEditarAllCaja request) {
        ResponseEditarAllCaja rpt = new ResponseEditarAllCaja();
        String SQL = "{ call CAJA.sp_EditarCaja(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdCaja());
            setParameter(pstmt, 2, request.getDescripcion());
            Long sucursalId = 1L;
            pstmt.setLong(3, sucursalId);
            Long userId = 1L;
            pstmt.setLong(4, userId);
            Long empresaId = 1L;
            pstmt.setLong(5, empresaId);

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

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
