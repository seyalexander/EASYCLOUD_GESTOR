package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestDetalleAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseDetalleAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.domain.interfaces.IAperturaCajaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.model.AperturaCajaModel;
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
public class AperturaCajaDetalleRepository implements IAperturaCajaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleAperturaCaja DetalleAperturaCaja(RequestDetalleAperturaCaja request) {
        ResponseDetalleAperturaCaja response = new ResponseDetalleAperturaCaja();
        String SQL = "{ call CAJA.sp_ObtenerAperturaCajaPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdAperturaCaja());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AperturaCajaModel item = new AperturaCajaModel();
                    item.setIdAperturacaja(rs.getLong("idAperturacaja"));
                    item.setIdSucursal(rs.getLong("idSucursal"));
                    item.setIdUsuario(rs.getLong("idUsuario"));
                    item.setFechaApertura((rs.getTimestamp("fechaApertura") != null ? rs.getTimestamp("fechaApertura").toLocalDateTime() : null));
                    item.setMontoInical(rs.getDouble("montoInical"));
                    item.setEstado(rs.getObject("estado"));
                    response.setExito(true);
                    response.setMessage("AperturaCaja obtenido correctamente.");
                    response.setAperturaCaja(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró AperturaCaja.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ObtenerAperturaCajaPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
