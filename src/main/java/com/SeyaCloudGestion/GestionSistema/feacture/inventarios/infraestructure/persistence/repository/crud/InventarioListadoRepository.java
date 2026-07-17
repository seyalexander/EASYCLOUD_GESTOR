package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestListaInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseListaInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces.IInventarioListado;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.EstadoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.InventarioModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class InventarioListadoRepository implements IInventarioListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaInventario listaInventario(RequestListaInventario request) {
        ResponseListaInventario rpt = new ResponseListaInventario();
        List<InventarioModel> registros = new ArrayList<>();
        String SQL = "{ call INVENTARIO.sp_ListarInventarioCabecera(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId = 1L;
            Long sucursalId = 1L;

            setParameter(pstmt, 1, empresaId);
            setParameter(pstmt, 2, sucursalId);
            setParameter(pstmt, 3, request.getIdAlmacen());
            setParameter(pstmt, 4, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    InventarioModel item = new InventarioModel();
                    item.setIdInventarioCabecera(rs.getLong("idInventarioCabecera"));
                    item.setIdAlmacen(rs.getLong("idAlmacen"));
                    Timestamp fechaInventario = rs.getTimestamp("fechaInventario");
                    if (fechaInventario != null) {
                        item.setFechaInventario(fechaInventario.toLocalDateTime());
                    }

                    Timestamp fechaCierre = rs.getTimestamp("fechaCierreInventario");
                    if (fechaCierre != null) {
                        item.setFechaCierreInventario(fechaCierre.toLocalDateTime());
                    }
                    item.setObservacion(rs.getString("observacion"));
                    item.setEstado(EstadoInventario.valueOf(rs.getString("estado")));
                    item.setIdUsuario(rs.getLong("idUsuario"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setInventarios(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarInventario", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
