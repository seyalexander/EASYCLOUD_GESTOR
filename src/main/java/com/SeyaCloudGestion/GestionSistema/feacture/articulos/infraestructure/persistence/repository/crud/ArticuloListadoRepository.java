package com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestListaArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseListaArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces.IArticulosListado;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces.IArticulosRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.model.ArticulosModel;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseRegistroFamilia;
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
public class ArticuloListadoRepository implements IArticulosListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaArticulo ListaArticulos(RequestListaArticulo request) {
        ResponseListaArticulo rpt = new ResponseListaArticulo();
        List<ArticulosModel> articulos = new ArrayList<>();

        String SQL = "{ call PRODUCTOS.sp_ListarArticulos (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
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

                articulos.add(item);
            }

            rpt.setExito(true);
            rpt.setArticulos(articulos);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            rpt.setArticulos(java.util.List.of());
        }

        return rpt;
    }

}
