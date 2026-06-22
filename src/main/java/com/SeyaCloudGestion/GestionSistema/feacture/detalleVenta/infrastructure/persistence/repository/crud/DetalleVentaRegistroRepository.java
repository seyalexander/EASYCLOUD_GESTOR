package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.infrastructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.interfaces.IDetalleVentaRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseRegistroVenta;
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
public class DetalleVentaRegistroRepository implements IDetalleVentaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    public ResponseRegistroDetalleVenta registrarDetalleVenta(RequestRegistroDetalleVenta request,double total) {
        ResponseRegistroDetalleVenta rpt = new ResponseRegistroDetalleVenta();

        String SQL = "{ call VENTAS.sp_RegistroVentaDetalle(?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdVenta());
            pstmt.setLong(2, request.getIdArticulo());
            pstmt.setLong(3, request.getIdAlmacen());
            pstmt.setDouble(4, request.getCantidad());
            pstmt.setDouble(5, request.getPrecioUnitario());
            pstmt.setDouble(6, request.getDescuento());
            pstmt.setDouble(7, total);
            Long userId= 1L;
            pstmt.setLong(8, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Detalle de venta insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el detalle de la venta.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroVentaDetalle", e);
        }

        return rpt;
    }
}