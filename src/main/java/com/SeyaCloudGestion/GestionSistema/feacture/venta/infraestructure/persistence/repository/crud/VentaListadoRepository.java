package com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestListaVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseListaVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces.IVentaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.VentaModel;
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
public class VentaListadoRepository implements IVentaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaVenta listaVenta(RequestListaVenta request) {
        ResponseListaVenta rpt = new ResponseListaVenta();
        List<VentaModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarVenta(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            Long empresaId = 1L;
            Long sucursalId = 1L;

            setParameter(pstmt, 1, request.getEstado());
            pstmt.setLong(2, sucursalId);
            pstmt.setLong(3, empresaId);


            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    VentaModel item = new VentaModel();
                item.setIdVenta(rs.getLong("idVenta"));
                item.setIdCliente(rs.getLong("idCliente"));
                item.setIdUsuario(rs.getLong("idUsuario"));
                item.setIdSucursal(rs.getLong("idSucursal"));
                item.setIdTurnoCaja(rs.getLong("idTurnoCaja"));
                    item.setFechaVenta(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );
                item.setSubTotal(rs.getDouble("subTotal"));
                item.setImpuesto(rs.getDouble("impuesto"));
                item.setTotal(rs.getDouble("total"));
                item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setVentas(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarVenta", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
