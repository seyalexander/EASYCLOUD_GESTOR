package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestDetalleSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseDetalleSerieCaja;
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

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class SerieCajaDetalleRepository implements ISerieCajaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleSerieCaja DetalleSerieCaja(RequestDetalleSerieCaja request) {
        ResponseDetalleSerieCaja response = new ResponseDetalleSerieCaja();
        String SQL = "{ call CAJA.sp_ObtenerCajaSeriePorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdSerieCaja());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SerieCajaModel item = new SerieCajaModel();
                    item.setIdCajaSerie(rs.getLong("idCajaSerie"));
                    item.setIdCaja(rs.getLong("idCaja"));
                    item.setIdSerieDocumento(rs.getLong("idSerieDocumento"));
                    item.setSerie(rs.getString("serie"));
                    item.setTipoComprobante(rs.getString("tipoComprobante"));
                    response.setExito(true);
                    response.setMessage("CierreCaja obtenido correctamente.");
                    response.setSerieCaja(item);
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
