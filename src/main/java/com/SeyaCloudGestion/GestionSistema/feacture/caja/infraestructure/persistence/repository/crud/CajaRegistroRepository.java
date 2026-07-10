package com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseRegistroCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces.ICajaRegistro;
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
public class CajaRegistroRepository implements ICajaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroCaja RegistroCaja(RequestRegistroCaja request) {
        ResponseRegistroCaja rpt = new ResponseRegistroCaja();

        String SQL = "{ call CAJA.sp_RegistroCaja(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            Long sucursalId = 1L;
            pstmt.setLong(2, sucursalId);
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

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
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe una caja con esa descripción.");
            } else {
                rpt.setMessage("Error al registrar la caja.");
            }
            log.error("Error en CAJA.sp_RegistroCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
