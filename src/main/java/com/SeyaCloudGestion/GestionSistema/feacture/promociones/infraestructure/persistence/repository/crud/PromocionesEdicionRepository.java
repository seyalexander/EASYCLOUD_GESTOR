package com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestEditarAllPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestEditarEstadoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseEditarAllPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseEditarEstadoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.domain.interfaces.IPromocionesEdicion;
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
public class PromocionesEdicionRepository implements IPromocionesEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllPromociones EditarAllPromociones(RequestEditarAllPromociones request) {
        ResponseEditarAllPromociones rpt = new ResponseEditarAllPromociones();
        String SQL = "{ call PRODUCTOS.sp_EditarPromociones(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Promociones actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Promociones.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarPromociones", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoPromociones EditarEstadoPromociones(RequestEditarEstadoPromociones request, int estado) {
        ResponseEditarEstadoPromociones rpt = new ResponseEditarEstadoPromociones();
        String SQL = "{ call PRODUCTOS.sp_EditarPromociones_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdPromociones());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Promociones actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Promociones.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarPromociones_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
