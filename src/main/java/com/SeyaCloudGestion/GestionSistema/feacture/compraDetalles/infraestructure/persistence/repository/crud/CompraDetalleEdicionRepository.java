package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.request.RequestEditarAllCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.request.RequestEditarEstadoCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response.ResponseEditarAllCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response.ResponseEditarEstadoCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.domain.interfaces.ICompraDetalleEdicion;
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
public class CompraDetalleEdicionRepository implements ICompraDetalleEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllCompraDetalle EditarAllCompraDetalle(RequestEditarAllCompraDetalle request) {
        ResponseEditarAllCompraDetalle rpt = new ResponseEditarAllCompraDetalle();
        String SQL = "{ call COMPRAS.sp_EditarCompraDetalle(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CompraDetalle actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó CompraDetalle.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarCompraDetalle", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoCompraDetalle EditarEstadoCompraDetalle(RequestEditarEstadoCompraDetalle request, int estado) {
        ResponseEditarEstadoCompraDetalle rpt = new ResponseEditarEstadoCompraDetalle();
        String SQL = "{ call COMPRAS.sp_EditarCompraDetalle_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CompraDetalle actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó CompraDetalle.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarCompraDetalle_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
