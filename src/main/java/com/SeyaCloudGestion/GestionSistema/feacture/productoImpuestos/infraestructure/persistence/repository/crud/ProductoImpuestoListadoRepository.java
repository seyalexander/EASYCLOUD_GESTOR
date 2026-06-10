package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestListaProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseListaProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.interfaces.IProductoImpuestoListado;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.model.ProductoImpuestoModel;
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
public class ProductoImpuestoListadoRepository implements IProductoImpuestoListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaProductoImpuesto ListaProductoImpuesto(RequestListaProductoImpuesto request) {
        ResponseListaProductoImpuesto rpt = new ResponseListaProductoImpuesto();
        List<ProductoImpuestoModel> registros = new ArrayList<>();
        String SQL = "{ call PRODUCTOS.sp_ListarProductoImpuesto(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

        pstmt.setInt(1,request.getEstado());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    ProductoImpuestoModel item = new ProductoImpuestoModel();
                item.setIdProductoImpuesto(rs.getLong("idProductoImpuesto"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setPorcentaje(rs.getDouble("porcentaje"));
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
            rpt.setProductoImpuestos(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ListarProductoImpuesto", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
