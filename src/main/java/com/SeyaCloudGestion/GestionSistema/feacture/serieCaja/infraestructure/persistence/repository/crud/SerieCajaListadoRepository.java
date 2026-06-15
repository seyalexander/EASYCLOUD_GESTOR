package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.domain.interfaces.ISerieCajaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.domain.interfaces.ISerieCajaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.model.SerieCajaModel;
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
public class SerieCajaListadoRepository implements ISerieCajaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaSerieCaja listaSerieCaja(RequestListaSerieCaja request) {
        ResponseListaSerieCaja rpt = new ResponseListaSerieCaja();
        List<SerieCajaModel> registros = new ArrayList<>();
        String SQL = "{ call CAJA.sp_ListarCajaSeriePorCaja(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCaja());

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    SerieCajaModel item = new SerieCajaModel();
                item.setIdCajaSerie(rs.getLong("idCajaSerie"));
                item.setIdCaja(rs.getLong("idCaja"));
                item.setIdSerieDocumento(rs.getLong("idSerieDocumento"));
                item.setSerie(rs.getString("serie"));
                item.setTipoComprobante(rs.getString("tipoComprobante"));
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setSerieCajas(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ListarCajaSeriePorCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
