package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestRegistroPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseRegistroPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces.IPagoProveedoresRegistro;
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
public class PagoProveedorRegistroRepository implements IPagoProveedoresRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroPagoProveedor RegistroPagoProveedor(RequestRegistroPagoProveedor request) {
        ResponseRegistroPagoProveedor rpt = new ResponseRegistroPagoProveedor();
        String SQL = "{ call COMPRAS.sp_RegistrarPagoProveedor(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCuentaPorPagar());
            setParameter(pstmt, 2, request.getMontoPagado());
            setParameter(pstmt, 3, request.getIdTipoPago());
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);
            Long sucursalId = 1L;
            pstmt.setLong(5, sucursalId);
            Long userId = 1L;
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("pago insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el pago.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_RegistrarPagoProveedor", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
