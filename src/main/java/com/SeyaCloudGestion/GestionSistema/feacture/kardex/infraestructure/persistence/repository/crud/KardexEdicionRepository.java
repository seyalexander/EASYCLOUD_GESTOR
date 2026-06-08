package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestEditarAllKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestEditarEstadoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseEditarAllKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseEditarEstadoKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces.IKardexEdicion;
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
public class KardexEdicionRepository implements IKardexEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllKardex EditarAllKardex(RequestEditarAllKardex request) {
        ResponseEditarAllKardex rpt = new ResponseEditarAllKardex();
        String SQL = "{ call ALMACEN.sp_EditarKardex(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Kardex actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Kardex.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarKardex", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoKardex EditarEstadoKardex(RequestEditarEstadoKardex request, int estado) {
        ResponseEditarEstadoKardex rpt = new ResponseEditarEstadoKardex();
        String SQL = "{ call ALMACEN.sp_EditarKardex_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdKardex());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Kardex actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Kardex.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_EditarKardex_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
