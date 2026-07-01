package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.infrastructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.interfaces.IDetalleCompraListado;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.infrastructure.persistence.model.DetalleCompraModel;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;
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
public class DetalleCompraListadoRepository implements IDetalleCompraListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    public ResponseListaDetalleCompra listarDetalleCompra(RequestListaDetalleCompra request) {
        ResponseListaDetalleCompra rpt = new ResponseListaDetalleCompra();
        List<DetalleCompraModel> detalles = new ArrayList<>();

        String SQL = "{ call COMPRAS.sp_ListarCompraDetalle (?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdCompra());
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                DetalleCompraModel item = new DetalleCompraModel();

                item.setIdCompraDetalle(rs.getLong("idCompraDetalle"));
                item.setIdCompra(rs.getLong("idCompra"));
                item.setIdArticulo(rs.getLong("idArticulo"));

                item.setCantidad(rs.getDouble("cantidad"));
                item.setCostoUnitario(rs.getDouble("costoUnitario"));
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
            rpt.setMessage("Detalles de la compra cargados correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage("Error al obtener los detalles de la compra: " + e.getMessage());
        }

        return rpt;
    }
}