package com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces.IArticulosDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.model.ArticulosModel;
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
public class ArticuloDetalleRepository implements IArticulosDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleArticulo DetalleArticulos(RequestDetalleArticulo request) {
        ResponseDetalleArticulo response = new ResponseDetalleArticulo();
        String SQL = "{ call PRODUCTOS.sp_ObtenerArticulosPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdArticulo());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ArticulosModel item = new ArticulosModel();
                    item.setIdArticulos(rs.getLong("idArticulos"));
                    item.setImagenUrl(rs.getString("imagenUrl"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setCodigoArticulo(rs.getString("codigoArticulo"));
                    item.setCodigoBarras(rs.getString("codigoBarras"));
                    item.setPrecioVenta(rs.getFloat("precioVenta"));
                    item.setEstado(rs.getInt("estado"));
                    item.setIsFamilia(rs.getLong("isFamilia"));
                    item.setDescripcionFamilia(rs.getString("descripcionFamilia"));
                    item.setIdSubFamilia(rs.getLong("idSubFamilia"));
                    item.setDescripcionSubFamilia(rs.getString("descripcionSubFamilia"));
                    item.setIdUnidadMedida(rs.getLong("idUnidadMedida"));
                    item.setDescripcionUnidadMedida(rs.getString("descripcionUnidadMedida"));
                    item.setCostoCompra(rs.getFloat("costoCompra"));
                    item.setStockMinimo(rs.getFloat("stockMinimo"));
                    item.setIdMarca(rs.getLong("idMarca"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
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
                    response.setMessage("Articulos obtenido correctamente.");
                    response.setArticulos(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Articulos.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ObtenerArticulosPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
