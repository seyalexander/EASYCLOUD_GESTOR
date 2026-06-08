package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestListaNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseListaNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces.INotaCreditoListado;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.model.NotaCreditoModel;
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
public class NotaCreditoListadoRepository implements INotaCreditoListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaNotaCredito listaNotaCredito(RequestListaNotaCredito request) {
        ResponseListaNotaCredito rpt = new ResponseListaNotaCredito();
        List<NotaCreditoModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarNotaCredito() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NotaCreditoModel item = new NotaCreditoModel();
                item.setIdNotaCredito(rs.getLong("idNotaCredito"));
                item.setIdVenta(rs.getLong("idVenta"));
                item.setMotivo(rs.getString("motivo"));
                item.setFechaEmision((rs.getTimestamp("fechaEmision") != null ? rs.getTimestamp("fechaEmision").toLocalDateTime() : null));
                item.setTotal(rs.getDouble("total"));
                item.setEstado(rs.getInt("estado"));
                item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setNotaCreditos(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarNotaCredito", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
