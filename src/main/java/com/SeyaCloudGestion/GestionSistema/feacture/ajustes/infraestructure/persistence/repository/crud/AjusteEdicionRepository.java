package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestEditarAllAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestEditarEstadoAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseEditarAllAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseEditarEstadoAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces.IAjustesEdicion;
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
public class AjusteEdicionRepository implements IAjustesEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllAjuste EditarAllAjustes(RequestEditarAllAjuste request) {
        ResponseEditarAllAjuste rpt = new ResponseEditarAllAjuste();
        String SQL = "{ call ALMACEN.sp_EditarAjustes(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Ajustes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Ajustes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarAjustes", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoAjuste EditarEstadoAjustes(RequestEditarEstadoAjuste request, int estado) {
        ResponseEditarEstadoAjuste rpt = new ResponseEditarEstadoAjuste();
        String SQL = "{ call ALMACEN.sp_EditarAjustes_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdAjuste());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Ajustes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Ajustes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarAjustes_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
