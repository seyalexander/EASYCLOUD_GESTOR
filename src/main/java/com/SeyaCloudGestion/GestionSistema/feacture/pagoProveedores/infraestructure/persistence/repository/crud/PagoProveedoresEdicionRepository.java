package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestEditarAllPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestEditarEstadoPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseEditarAllPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseEditarEstadoPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces.IPagoProveedoresEdicion;
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
public class PagoProveedoresEdicionRepository implements IPagoProveedoresEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllPagoProveedores EditarAllPagoProveedores(RequestEditarAllPagoProveedores request) {
        ResponseEditarAllPagoProveedores rpt = new ResponseEditarAllPagoProveedores();
        String SQL = "{ call COMPRAS.sp_EditarPagoProveedores(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("PagoProveedores actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó PagoProveedores.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarPagoProveedores", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoPagoProveedores EditarEstadoPagoProveedores(RequestEditarEstadoPagoProveedores request, int estado) {
        ResponseEditarEstadoPagoProveedores rpt = new ResponseEditarEstadoPagoProveedores();
        String SQL = "{ call COMPRAS.sp_EditarPagoProveedores_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdPagoProveedores());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("PagoProveedores actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó PagoProveedores.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarPagoProveedores_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
