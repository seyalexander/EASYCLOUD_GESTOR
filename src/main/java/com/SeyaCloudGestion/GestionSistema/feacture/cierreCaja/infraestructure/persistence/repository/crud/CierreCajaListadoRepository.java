package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestListaCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseListaCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.domain.interfaces.ICierreCajaListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class CierreCajaListadoRepository implements ICierreCajaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaCierreCaja listaCierreCaja(RequestListaCierreCaja request) {
        ResponseListaCierreCaja rpt = new ResponseListaCierreCaja();
        List<CierreCajaModel> registros = new ArrayList<>();
        String SQL = "{ call CAJA.sp_ListarCierreCaja(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CierreCajaModel item = new CierreCajaModel();
                item.setIdCierrecaja(rs.getLong("idCierrecaja"));
                item.setIdAperturaCaja(rs.getLong("idAperturaCaja"));
                item.setFechaCierre((rs.getTimestamp("fechaCierre") != null ? rs.getTimestamp("fechaCierre").toLocalDateTime() : null));
                item.setMontoSistema(rs.getDouble("montoSistema"));
                item.setMontoReal(rs.getDouble("montoReal"));
                item.setDiferencia(rs.getDouble("diferencia"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setCierreCajas(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ListarCierreCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
