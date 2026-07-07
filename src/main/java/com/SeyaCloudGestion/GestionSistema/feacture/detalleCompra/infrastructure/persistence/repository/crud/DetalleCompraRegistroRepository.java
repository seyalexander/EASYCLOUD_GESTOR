package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.infrastructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.interfaces.IDetalleCompraRegistro;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.interfaces.IDetalleVentaRegistro;
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
public class DetalleCompraRegistroRepository implements IDetalleCompraRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    public ResponseRegistroDetalleCompra registrarDetalleCompra(long idCompra,RequestRegistroDetalleCompra request, double total) {
        ResponseRegistroDetalleCompra rpt = new ResponseRegistroDetalleCompra();

        String SQL = "{ call COMPRAS.sp_RegistroCompraDetalle(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, idCompra);
            pstmt.setLong(2, request.getIdArticulo());
            pstmt.setDouble(3, request.getCantidad());
            pstmt.setDouble(4, request.getCostoUnitario());
            pstmt.setDouble(5, total);
            Long userId= 1L;
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Detalle de la compra insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el detalle de la compra.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_RegistroCompraDetalle", e);
        }

        return rpt;
    }
}