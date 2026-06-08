package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestRegistroAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseRegistroAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.domain.interfaces.IAperturaCajaRegistro;
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
public class AperturaCajaRegistroRepository implements IAperturaCajaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroAperturaCaja RegistroAperturaCaja(RequestRegistroAperturaCaja request) {
        ResponseRegistroAperturaCaja rpt = new ResponseRegistroAperturaCaja();

        String SQL = "{ call CAJA.sp_RegistroAperturaCaja(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdSucursal());
            setParameter(pstmt, 2, request.getIdUsuario());
            setParameter(pstmt, 3, request.getMontoInical());
            Long userId = 1L;
            pstmt.setLong(4, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("AperturaCaja insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó AperturaCaja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_RegistroAperturaCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
