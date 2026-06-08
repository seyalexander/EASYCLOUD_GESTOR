package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestRegistroPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseRegistroPagoProveedores;
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
public class PagoProveedoresRegistroRepository implements IPagoProveedoresRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroPagoProveedores RegistroPagoProveedores(RequestRegistroPagoProveedores request) {
        ResponseRegistroPagoProveedores rpt = new ResponseRegistroPagoProveedores();
        String SQL = "{ call COMPRAS.sp_RegistroPagoProveedores(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdCuentaPorPagar());
            setParameter(pstmt, 2, request.getFechaPago());
            setParameter(pstmt, 3, request.getMontoPagado());
            setParameter(pstmt, 4, request.getMetodoPago());
            Long userId = 1L;
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("PagoProveedores insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó PagoProveedores.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_RegistroPagoProveedores", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
