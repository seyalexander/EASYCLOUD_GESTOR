package com.SeyaCloudGestion.GestionSistema.feacture.guias.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestEditarAllGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestEditarEstadoGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseEditarAllGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseEditarEstadoGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.domain.interfaces.IGuiasIngresoEdicion;
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
public class GuiasIngresoEdicionRepository implements IGuiasIngresoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllGuiasIngreso EditarAllGuiasIngreso(RequestEditarAllGuiasIngreso request) {
        ResponseEditarAllGuiasIngreso rpt = new ResponseEditarAllGuiasIngreso();
        String SQL = "{ call COMPRAS.sp_EditarGuiasIngreso(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("GuiasIngreso actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó GuiasIngreso.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarGuiasIngreso", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoGuiasIngreso EditarEstadoGuiasIngreso(RequestEditarEstadoGuiasIngreso request, int estado) {
        ResponseEditarEstadoGuiasIngreso rpt = new ResponseEditarEstadoGuiasIngreso();
        String SQL = "{ call COMPRAS.sp_EditarGuiasIngreso_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdGuiasIngreso());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("GuiasIngreso actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó GuiasIngreso.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarGuiasIngreso_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
