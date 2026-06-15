package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.repository.crud;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestRegistroTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseRegistroTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces.ITipoComprobanteRegistro;
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
public class TipoComprobanteRegistroRepository implements ITipoComprobanteRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTipoComprobante RegistroTipoComprobante(RequestRegistroTipoComprobante request) {
        ResponseRegistroTipoComprobante rpt = new ResponseRegistroTipoComprobante();

        String SQL = "{ call CONFIGURACION.sp_RegistroTipoComprobante(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId = 1L;

            pstmt.setString(1, request.getDescripcion());
            pstmt.setString(2, request.getCodigoSunat());
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Tipo de comprobante registrado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se registró el tipo de comprobante.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);

            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un tipo comprobante con esa descripción.");
            } else {
                rpt.setMessage("Error al registrar el tipo comprobante.");
            }
            log.error("Error en CONFIGURACION.sp_RegistroTipoComprobante", e);
        }

        return rpt;
    }
}