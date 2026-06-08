package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.interfaces.ITipoPagosDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.model.TipoPagosModel;
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
public class TipoPagosDetalleRepository implements ITipoPagosDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleTipoPagos DetalleTipoPagos(RequestDetalleTipoPagos request) {
        ResponseDetalleTipoPagos response = new ResponseDetalleTipoPagos();
        String SQL = "{ call VENTAS.sp_ObtenerTipoPagoPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdTipoPago());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TipoPagosModel item = new TipoPagosModel();
                    item.setIdTipoPago(rs.getLong("idTipoPago"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setImagenUrl(rs.getString("imagenUrl"));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
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

                    item.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    item.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("TipoPagos obtenido correctamente.");
                    response.setTipoPagos(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró TipoPagos.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_ObtenerTipoPagosPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
