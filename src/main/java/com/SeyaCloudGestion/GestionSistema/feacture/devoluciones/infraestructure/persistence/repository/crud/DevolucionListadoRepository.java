package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestListaDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseListaDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces.IDevolucionListado;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.model.DevolucionModel;
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
public class DevolucionListadoRepository implements IDevolucionListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaDevolucion listaDevolucion(RequestListaDevolucion request) {
        ResponseListaDevolucion rpt = new ResponseListaDevolucion();
        List<DevolucionModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarDevolucion() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DevolucionModel item = new DevolucionModel();
                item.setIdDevolucion(rs.getLong("idDevolucion"));
                item.setIdVenta(rs.getLong("idVenta"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setCantidad(rs.getDouble("cantidad"));
                item.setMotivo(rs.getString("motivo"));
                item.setFechaDevolucion((rs.getTimestamp("fechaDevolucion") != null ? rs.getTimestamp("fechaDevolucion").toLocalDateTime() : null));
                item.setIdUsuario(rs.getLong("idUsuario"));
                item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setDevoluciones(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarDevolucion", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
