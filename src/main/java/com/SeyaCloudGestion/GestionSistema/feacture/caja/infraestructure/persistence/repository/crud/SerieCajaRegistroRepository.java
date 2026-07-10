package com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestRegistroSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseRegistroSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces.ISerieCajaRegistro;
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
public class SerieCajaRegistroRepository implements ISerieCajaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroSerieCaja RegistroSerieCaja(RequestRegistroSerieCaja request) {
        ResponseRegistroSerieCaja rpt = new ResponseRegistroSerieCaja();

        String SQL = "{ call CAJA.sp_RegistroCajaSerie(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long cajaId = 1L;
            pstmt.setLong(1, cajaId);
            setParameter(pstmt, 2, request.getIdSerieDocumento());
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Serie caja insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó la serie caja.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe una caja con esa serie.");
            } else {
                rpt.setMessage("Error al registrar la serie caja .");
            }
            log.error("Error en CAJA.sp_RegistroSerieCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
