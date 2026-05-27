package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.request.RequestEditarAllTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.request.RequestEditarEstadoTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response.ResponseEditarAllTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response.ResponseEditarEstadoTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.domain.interfaces.ITipoPromocionesEdicion;
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
public class TipoPromocionesEdicionRepository implements ITipoPromocionesEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllTipoPromociones EditarAllTipoPromociones(RequestEditarAllTipoPromociones request) {
        ResponseEditarAllTipoPromociones rpt = new ResponseEditarAllTipoPromociones();
        String SQL = "{ call PRODUCTOS.sp_EditarTipoPromociones(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoPromociones actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó TipoPromociones.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarTipoPromociones", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoTipoPromociones EditarEstadoTipoPromociones(RequestEditarEstadoTipoPromociones request, int estado) {
        ResponseEditarEstadoTipoPromociones rpt = new ResponseEditarEstadoTipoPromociones();
        String SQL = "{ call PRODUCTOS.sp_EditarTipoPromociones_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdTipoPromociones());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoPromociones actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó TipoPromociones.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarTipoPromociones_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
