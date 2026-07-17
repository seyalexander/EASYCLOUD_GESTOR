package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.interfaces.IDetalleInventarioListado;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.model.DetalleInventarioModel;
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
public class DetalleInventarioListadoRepository implements IDetalleInventarioListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    public ResponseListaDetalleInventario listarDetalleInventario(RequestListaDetalleInventario request) {
        ResponseListaDetalleInventario rpt = new ResponseListaDetalleInventario();
        List<DetalleInventarioModel> detalles = new ArrayList<>();

        String SQL = "{ call INVENTARIO.sp_ListarInventarioDetalle (?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdInventarioCabecera());
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);
            pstmt.setLong(4, request.getIdAlmacecn());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                DetalleInventarioModel item = new DetalleInventarioModel();

                item.setIdInventarioCabecera(rs.getLong("idInventarioDetalle"));
                item.setIdInventarioDetalle(rs.getLong("idInventarioCabecera"));
                item.setIdArticulo(rs.getLong("idArticulo"));

                item.setStockSistema(rs.getDouble("stockSistema"));
                item.setStockFisico(rs.getDouble("stockFisico"));
                item.setDiferencia(rs.getDouble("diferencia"));

                detalles.add(item);
            }

            rpt.setExito(true);
            rpt.setDetalles(detalles);
            rpt.setMessage("Detalles del inventario listados y  cargados correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage("Error al obtener los detalles del iventario: " + e.getMessage());
        }

        return rpt;
    }
}