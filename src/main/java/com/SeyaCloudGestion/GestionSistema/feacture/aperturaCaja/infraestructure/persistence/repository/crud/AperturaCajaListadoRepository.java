package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestListaAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseListaAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.domain.interfaces.IAperturaCajaListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class AperturaCajaListadoRepository implements IAperturaCajaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaAperturaCaja listaAperturaCaja(RequestListaAperturaCaja request) {
        ResponseListaAperturaCaja rpt = new ResponseListaAperturaCaja();
        List<AperturaCajaModel> registros = new ArrayList<>();
        String SQL = "{ call CAJA.sp_ListarAperturaCaja(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AperturaCajaModel item = new AperturaCajaModel();
                item.setIdAperturacaja(rs.getLong("idAperturacaja"));
                item.setIdSucursal(rs.getLong("idSucursal"));
                item.setIdUsuario(rs.getLong("idUsuario"));
                item.setFechaApertura((rs.getTimestamp("fechaApertura") != null ? rs.getTimestamp("fechaApertura").toLocalDateTime() : null));
                item.setMontoInical(rs.getDouble("montoInical"));
                item.setEstado(rs.getObject("estado"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setAperturaCajas(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ListarAperturaCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
