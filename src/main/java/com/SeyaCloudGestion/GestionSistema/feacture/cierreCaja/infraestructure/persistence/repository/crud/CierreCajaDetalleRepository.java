package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestDetalleCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseDetalleCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.domain.interfaces.ICierreCajaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.infraestructure.persistence.model.CierreCajaModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class CierreCajaDetalleRepository implements ICierreCajaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleCierreCaja DetalleCierreCaja(RequestDetalleCierreCaja request) {
        ResponseDetalleCierreCaja response = new ResponseDetalleCierreCaja();
        String SQL = "{ call CAJA.sp_ObtenerCierreCajaPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCierreCaja());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CierreCajaModel item = new CierreCajaModel();
                    item.setIdCierrecaja(rs.getLong("idCierrecaja"));
                    item.setIdAperturaCaja(rs.getLong("idAperturaCaja"));
                    item.setFechaCierre((rs.getTimestamp("fechaCierre") != null ? rs.getTimestamp("fechaCierre").toLocalDateTime() : null));
                    item.setMontoSistema(rs.getDouble("montoSistema"));
                    item.setMontoReal(rs.getDouble("montoReal"));
                    item.setDiferencia(rs.getDouble("diferencia"));
                    response.setExito(true);
                    response.setMessage("CierreCaja obtenido correctamente.");
                    response.setCierreCaja(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró CierreCaja.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ObtenerCierreCajaPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
