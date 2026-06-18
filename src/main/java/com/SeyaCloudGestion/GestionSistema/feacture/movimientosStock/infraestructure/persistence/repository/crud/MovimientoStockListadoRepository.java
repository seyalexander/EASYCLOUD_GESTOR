package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.domain.interfaces.IMovimientoStockListado;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.model.MovimientoStockModel;
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
public class MovimientoStockListadoRepository implements IMovimientoStockListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaMovimientoStock listaMovimientoStock(RequestListaMovimientoStock request) {
        ResponseListaMovimientoStock rpt = new ResponseListaMovimientoStock();
        List<MovimientoStockModel> registros = new ArrayList<>();
        String SQL = "{ call INVENTARIO.sp_ListarMovimientoStock(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId= 1L;
            Long sucursalId= 1L;
            setParameter(pstmt, 1, empresaId);
            setParameter(pstmt, 2, sucursalId);
            setParameter(pstmt, 3, request.getIdAlmacen());
            setParameter(pstmt, 4, request.getIdArticulo());

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    MovimientoStockModel item = new MovimientoStockModel();
                item.setIdMovimientoStock(rs.getLong("idMovimientoStock"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setIdAlmacen(rs.getLong("idAlmacen"));
                item.setIdTipoMovimiento(rs.getLong("idTipoMovimiento"));
                item.setCantidad(rs.getDouble("cantidad"));
                item.setCostoUnitario(rs.getDouble("costoUnitario"));
                item.setObservacion(rs.getString("observacion"));
                item.setFechaMovimiento((rs.getTimestamp("fechaMovimiento") != null ? rs.getTimestamp("fechaMovimiento").toLocalDateTime() : null));
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

                    registros.add(item);
            }

            rpt.setExito(true);
            rpt.setMovimientoStocks(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarMovimientoStock", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
