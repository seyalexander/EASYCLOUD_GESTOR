package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestListaTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseListaTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces.ITurnoCajaListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class TurnoCajaListadoRepository implements ITurnoCajaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaTurnoCaja ListaTurnoCaja(RequestListaTurnoCaja request) {
        ResponseListaTurnoCaja rpt = new ResponseListaTurnoCaja();
        List<TurnoCajaModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarTurnoCaja(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado().name());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    TurnoCajaModel item = new TurnoCajaModel();
                item.setIdTurnoCaja(rs.getLong("idTurnoCaja"));
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
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setTurnoCajas(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ListarTurnoCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
