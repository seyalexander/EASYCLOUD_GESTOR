package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.interfaces.IDetalleCompraRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestRegistroDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseRegistroDetalleInventario;
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
public class DetalleInventarioRegistroRepository implements IDetalleInventarioRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    public ResponseRegistroDetalleInventario registrarDetalleIventario(long idInventarioCabezera, RequestRegistroDetalleInventario request, double stockSistema ) {
        ResponseRegistroDetalleInventario rpt = new ResponseRegistroDetalleInventario();

        String SQL = "{ call INVENTARIO.sp_RegistroInventarioDetalle(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, idInventarioCabezera);
            pstmt.setLong(2, request.getIdArticulo());
            pstmt.setDouble(3, stockSistema);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Detalle Inventario insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el detalle del inventario.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_RegistroInventarioDetalle", e);
        }

        return rpt;
    }
}