package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.infrastructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseListaDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.interfaces.IDetalleVentaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.infrastructure.persistence.model.DetalleVentaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta; // O el DTO de listado/detalle que uses
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional("sqlServerTransactionManager")
public class DetalleVentaListadoRepository implements IDetalleVentaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    public ResponseListaDetalleVenta listarDetalleVenta(RequestDetalleVenta request) {
        ResponseListaDetalleVenta rpt = new ResponseListaDetalleVenta();
        List<DetalleVentaModel> detalles = new ArrayList<>();

        String SQL = "{ call VENTAS.sp_ListarVentaDetalle (?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdVenta());
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                DetalleVentaModel item = new DetalleVentaModel();

                item.setIdVentaDetalle(rs.getLong("idVentaDetalle"));
                item.setIdVenta(rs.getLong("idVenta"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setIdAlmacen(rs.getLong("idAlmacen"));

                item.setCantidad(rs.getDouble("cantidad"));
                item.setPrecioUnitario(rs.getDouble("precioUnitario"));
                item.setDescuento(rs.getDouble("descuento"));
                item.setTotal(rs.getDouble("total"));

                item.setFechaIngreso(
                        rs.getTimestamp("fechaIngreso") != null
                                ? rs.getTimestamp("fechaIngreso").toLocalDateTime()
                                : null
                );
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

                detalles.add(item);
            }

            rpt.setExito(true);
            rpt.setDetalles(detalles);
            rpt.setMessage("Detalles de la venta cargados correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage("Error al obtener los detalles de la venta: " + e.getMessage());
        }

        return rpt;
    }
}