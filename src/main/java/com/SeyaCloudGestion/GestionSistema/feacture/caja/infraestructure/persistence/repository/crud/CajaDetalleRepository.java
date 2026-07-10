package com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces.ICajaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.model.CajaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.model.Estado;
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
public class CajaDetalleRepository implements ICajaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleCaja DetalleCaja(RequestDetalleCaja request) {
        ResponseDetalleCaja response = new ResponseDetalleCaja();
        String SQL = "{ call CAJA.sp_ObtenerCajaPorId(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCaja());
            Long sucursalId = 1L;
            pstmt.setLong(2, sucursalId);
            Long empresaId = 1L;
            pstmt.setLong(3, empresaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CajaModel item = new CajaModel();
                    item.setIdCaja(rs.getLong("idCaja"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));

                    response.setExito(true);
                    response.setMessage("AperturaCaja obtenido correctamente.");
                    response.setCaja(item);
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
