package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseEditarDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.interfaces.IDetalleInventarioEditar;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.interfaces.IDetalleInventarioRegistro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class DetalleInventarioEdicionRepository implements IDetalleInventarioEditar {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    public ResponseEditarDetalleInventario editarDetalleIventario(long idInventarioCabezera, RequestEditarDetalleInventario request ) {
        ResponseEditarDetalleInventario rpt = new ResponseEditarDetalleInventario();

        String SQL = "{ call INVENTARIO.sp_ActualizarStockFisicoDetalle(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, idInventarioCabezera);
            pstmt.setLong(2, request.getIdArticulo());
            pstmt.setDouble(3, request.getStockFisico());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Detalle Inventario editado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se editó el detalle del inventario.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ActualizarStockFisicoDetalle", e);
        }

        return rpt;
    }
}