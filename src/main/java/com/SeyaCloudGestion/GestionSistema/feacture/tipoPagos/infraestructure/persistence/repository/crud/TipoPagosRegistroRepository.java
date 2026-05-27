package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestRegistroTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseRegistroTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.interfaces.ITipoPagosRegistro;
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
public class TipoPagosRegistroRepository implements ITipoPagosRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTipoPagos RegistroTipoPagos(RequestRegistroTipoPagos request) {
        ResponseRegistroTipoPagos rpt = new ResponseRegistroTipoPagos();
        String SQL = "{ call CONFIGURACION.sp_RegistroTipoPagos(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            setParameter(pstmt, 2, request.getImagenUrl());
            setParameter(pstmt, 3, request.getFechaIngreso());
            Long userId = 1L;
            pstmt.setLong(4, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoPagos insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó TipoPagos.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_RegistroTipoPagos", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
