package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces.IInventarioDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.EstadoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.InventarioModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class InventarioDetalleRepository implements IInventarioDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleInventario DetalleInventario(RequestDetalleInventario request) {
        ResponseDetalleInventario response = new ResponseDetalleInventario();
        String SQL = "{ call INVENTARIO.sp_ObtenerInventarioCabecera(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId = 1L;
            Long sucursalId = 1L;
            setParameter(pstmt, 1, empresaId);
            setParameter(pstmt, 2, sucursalId);
            setParameter(pstmt, 3, request.getIdInventario());


            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
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

                    response.setExito(true);
                    response.setMessage("Inventario obtenido correctamente.");
                    response.setInventario(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Inventario.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ObtenerInventarioCabecera", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
