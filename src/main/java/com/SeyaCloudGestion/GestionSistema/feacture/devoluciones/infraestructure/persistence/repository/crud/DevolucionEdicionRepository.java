package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestEditarAllDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestEditarEstadoDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseEditarAllDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseEditarEstadoDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces.IDevolucionEdicion;
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
public class DevolucionEdicionRepository implements IDevolucionEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllDevolucion EditarAllDevolucion(RequestEditarAllDevolucion request) {
        ResponseEditarAllDevolucion rpt = new ResponseEditarAllDevolucion();
        String SQL = "{ call VENTAS.sp_EditarDevolucion(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Devolucion actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Devolucion.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarDevolucion", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoDevolucion EditarEstadoDevolucion(RequestEditarEstadoDevolucion request, int estado) {
        ResponseEditarEstadoDevolucion rpt = new ResponseEditarEstadoDevolucion();
        String SQL = "{ call VENTAS.sp_EditarDevolucion_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Devolucion actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Devolucion.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_EditarDevolucion_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
