package com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestListaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseListaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces.ICajaListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class CajaListadoRepository implements ICajaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaCaja listaCaja() {
        ResponseListaCaja rpt = new ResponseListaCaja();
        List<CajaModel> registros = new ArrayList<>();
        String SQL = "{ call CAJA.sp_ListarCaja(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            Long empresaId = 1L;
            pstmt.setLong(1, empresaId);

            Long sucursalId = 1L;
            pstmt.setLong(2, sucursalId);

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
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
                    registros.add(item);
            }

            rpt.setExito(true);
            rpt.setCajas(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CAJA.sp_ListarCaja", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
