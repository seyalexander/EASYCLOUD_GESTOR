package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseDetalleUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces.IUnidadMedidaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.model.UnidadMedidaModel;
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
public class UnidadMedidaDetalleRepository implements IUnidadMedidaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleUnidadMedida DetalleUnidadMedida(RequestDetalleUnidadMedida request) {
        ResponseDetalleUnidadMedida response = new ResponseDetalleUnidadMedida();
        String SQL = "{ call PRODUCTOS.sp_ObtenerUnidadMedidaPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdUnidadMedida());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UnidadMedidaModel item = new UnidadMedidaModel();
                    item.setIdUnidadMedida(rs.getLong("idUnidadMedida"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setSiglas(rs.getString("siglas"));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaCreacion((rs.getTimestamp("fechaCreacion") != null ? rs.getTimestamp("fechaCreacion").toLocalDateTime() : null));
                    item.setFechaEdicion((rs.getTimestamp("fechaEdicion") != null ? rs.getTimestamp("fechaEdicion").toLocalDateTime() : null));
                    item.setFechaAnulacion((rs.getTimestamp("fechaAnulacion") != null ? rs.getTimestamp("fechaAnulacion").toLocalDateTime() : null));
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    item.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));
                    item.setUsuarioCreacion(rs.getString("usuarioCreacion"));
                    item.setUsuarioEdicion(rs.getString("usuarioEdicion"));
                    item.setUsuarioAnulacion(rs.getString("usuarioAnulacion"));
                    response.setExito(true);
                    response.setMessage("UnidadMedida obtenido correctamente.");
                    response.setUnidadMedida(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró UnidadMedida.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ObtenerUnidadMedidaPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
