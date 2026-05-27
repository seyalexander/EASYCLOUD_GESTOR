package com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestListaCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseListaCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces.ICompraListado;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.model.CompraModel;
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
public class CompraListadoRepository implements ICompraListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaCompra listaCompra(RequestListaCompra request) {
        ResponseListaCompra rpt = new ResponseListaCompra();
        List<CompraModel> registros = new ArrayList<>();
        String SQL = "{ call COMPRAS.sp_ListarCompra() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CompraModel item = new CompraModel();
                item.setIdCompra(rs.getLong("idCompra"));
                item.setIdProveedor(rs.getLong("idProveedor"));
                item.setIdSucursal(rs.getLong("idSucursal"));
                item.setFechaCompra(rs.getString("fechaCompra"));
                item.setSubTotal(rs.getDouble("subTotal"));
                item.setImpuesto(rs.getDouble("impuesto"));
                item.setTotal(rs.getDouble("total"));
                item.setEstado(rs.getInt("estado"));
                item.setFechaIngreso(rs.getString("fechaIngreso"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setCompras(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ListarCompra", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
