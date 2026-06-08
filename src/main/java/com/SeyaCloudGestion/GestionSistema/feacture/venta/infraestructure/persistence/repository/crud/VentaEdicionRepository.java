package com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestEditarAllVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestEditarEstadoVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseEditarAllVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseEditarEstadoVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces.IVentaEdicion;
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
public class VentaEdicionRepository implements IVentaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllVenta EditarAllVenta(RequestEditarAllVenta request) {
        ResponseEditarAllVenta rpt = new ResponseEditarAllVenta();
        String SQL = "{ call VENTAS.sp_EditarVenta(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Venta actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Venta.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarVenta", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoVenta EditarEstadoVenta(RequestEditarEstadoVenta request, int estado) {
        ResponseEditarEstadoVenta rpt = new ResponseEditarEstadoVenta();
        String SQL = "{ call VENTAS.sp_EditarVenta_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdVenta());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Venta actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Venta.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarVenta_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
