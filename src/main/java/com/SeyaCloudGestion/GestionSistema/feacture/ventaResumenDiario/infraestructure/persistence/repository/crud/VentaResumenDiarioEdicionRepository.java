package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.request.RequestEditarAllVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.request.RequestEditarEstadoVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.response.ResponseEditarAllVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.response.ResponseEditarEstadoVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.domain.interfaces.IVentaResumenDiarioEdicion;
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
public class VentaResumenDiarioEdicionRepository implements IVentaResumenDiarioEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllVentaResumenDiario EditarAllVentaResumenDiario(RequestEditarAllVentaResumenDiario request) {
        ResponseEditarAllVentaResumenDiario rpt = new ResponseEditarAllVentaResumenDiario();
        String SQL = "{ call VENTAS.sp_EditarVentaResumenDiario(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("VentaResumenDiario actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó VentaResumenDiario.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarVentaResumenDiario", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoVentaResumenDiario EditarEstadoVentaResumenDiario(RequestEditarEstadoVentaResumenDiario request, int estado) {
        ResponseEditarEstadoVentaResumenDiario rpt = new ResponseEditarEstadoVentaResumenDiario();
        String SQL = "{ call VENTAS.sp_EditarVentaResumenDiario_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdVentaResumenDiario());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("VentaResumenDiario actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó VentaResumenDiario.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarVentaResumenDiario_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
