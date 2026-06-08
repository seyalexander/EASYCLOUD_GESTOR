package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.request.RequestListaCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response.ResponseListaCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.domain.interfaces.ICompraDetalleListado;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.model.CompraDetalleModel;
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
public class CompraDetalleListadoRepository implements ICompraDetalleListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaCompraDetalle listaCompraDetalle(RequestListaCompraDetalle request) {
        ResponseListaCompraDetalle rpt = new ResponseListaCompraDetalle();
        List<CompraDetalleModel> registros = new ArrayList<>();
        String SQL = "{ call COMPRAS.sp_ListarCompraDetalle() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CompraDetalleModel item = new CompraDetalleModel();
                item.setIdCompraDetalle(rs.getLong("idCompraDetalle"));
                item.setIdCompra(rs.getLong("idCompra"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setCantidad(rs.getDouble("cantidad"));
                item.setCostoUnitario(rs.getDouble("costoUnitario"));
                item.setTotal(rs.getDouble("total"));
                item.setFechaIngreso(rs.getString("fechaIngreso"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setCompraDetalles(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ListarCompraDetalle", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
