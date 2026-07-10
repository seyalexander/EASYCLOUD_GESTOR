package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces.ITurnoCajaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.TurnoCajaModel;
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
public class TurnoCajaDetalleRepository implements ITurnoCajaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleTurnoCaja DetalleTurnoCaja(RequestDetalleTurnoCaja request,EstadoCaja estado) {
        ResponseDetalleTurnoCaja response = new ResponseDetalleTurnoCaja();
        String SQL = "{ call VENTAS.sp_ObtenerTurnoCajaPorId(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCaja());
            setParameter(pstmt, 2, estado != null ? estado.name() : null);
            Long empresaId = 1L;
            pstmt.setLong(3, empresaId);
            Long sucursalId = 1L;
            pstmt.setLong(4, sucursalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TurnoCajaModel item = new TurnoCajaModel();
                    item.setIdTurnoCaja(rs.getLong("idTurnoCaja"));
                    item.setIdCaja(rs.getLong("idCaja"));
                    item.setIdUsuario(rs.getLong("idUsuario"));
                    item.setIdSucursal(rs.getLong("idSucursal"));
                    item.setFechaApertura((rs.getTimestamp("fechaApertura") != null ? rs.getTimestamp("fechaApertura").toLocalDateTime() : null));
                    item.setFechaCierre((rs.getTimestamp("fechaCierre") != null ? rs.getTimestamp("fechaCierre").toLocalDateTime() : null));
                    item.setMontoInicial(rs.getDouble("montoInicial"));
                    item.setMontoReal(rs.getDouble("montoReal"));
                    item.setEstado(EstadoCaja.valueOf(rs.getString("estado")));
                    item.setMontoSistema(rs.getDouble("montoSistema"));
                    item.setDiferencia(rs.getDouble("diferencia"));
                    /*
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
                     */
                    response.setExito(true);
                    response.setMessage("TurnoCaja obtenido correctamente.");
                    response.setTurnoCaja(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró TurnoCaja.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ObtenerTurnoCajaPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
