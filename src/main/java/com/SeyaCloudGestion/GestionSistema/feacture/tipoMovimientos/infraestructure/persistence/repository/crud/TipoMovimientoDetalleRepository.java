package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestDetallePorCodigoTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseDetalleTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.interfaces.ITipoMovimientoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.model.TipoMovimientoModel;
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
public class TipoMovimientoDetalleRepository implements ITipoMovimientoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleTipoMovimiento DetalleTipoMovimiento(RequestDetalleTipoMovimiento request) {
        ResponseDetalleTipoMovimiento response = new ResponseDetalleTipoMovimiento();
        String SQL = "{ call INVENTARIO.sp_ObtenerTipoMovimientoPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTipoMovimiento());
            Long empresaId = 1L;
            //pstmt.setLong(2, empresaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TipoMovimientoModel item = new TipoMovimientoModel();
                    item.setIdTipoMovimiento(rs.getLong("idTipoMovimiento"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setEsEntrada(rs.getInt("esEntrada"));
                    item.setEstado(rs.getInt("estado"));
                    item.setCodigoSistema(
                            TipoMovimientoKardex.valueOf(rs.getString("codigoSistema"))
                    );
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
                    response.setMessage("TipoMovimiento obtenido correctamente.");
                    response.setTipoMovimiento(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró TipoMovimiento.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ObtenerTipoMovimientoPorId", e);
        }
        return response;
    }

    @Override
    public ResponseDetalleTipoMovimiento DetalleTipoMovimiento(RequestDetallePorCodigoTipoMovimiento request) {
        ResponseDetalleTipoMovimiento response = new ResponseDetalleTipoMovimiento();
        String SQL = "{ call INVENTARIO.sp_ObtenerTipoMovimientoPorCodigoSistema(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setString(1, request.getCodigo().name());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TipoMovimientoModel item = new TipoMovimientoModel();
                    item.setIdTipoMovimiento(rs.getLong("idTipoMovimiento"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setEsEntrada(rs.getInt("esEntrada"));
                    item.setEstado(rs.getInt("estado"));
                    item.setCodigoSistema(
                            TipoMovimientoKardex.valueOf(rs.getString("codigoSistema"))
                    );
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
                    response.setMessage("TipoMovimiento obtenido correctamente por código de sistema.");
                    response.setTipoMovimiento(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró TipoMovimiento para el código: " + request.getCodigo());
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ObtenerTipoMovimientoPorCodigoSistema", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
