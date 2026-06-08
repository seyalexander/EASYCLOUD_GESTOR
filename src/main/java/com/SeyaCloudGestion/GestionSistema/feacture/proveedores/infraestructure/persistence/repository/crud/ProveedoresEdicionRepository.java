package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarAllProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarEstadoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarAllProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseEditarEstadoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresEdicion;
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
public class ProveedoresEdicionRepository implements IProveedoresEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllProveedores EditarAllProveedores(RequestEditarAllProveedores request) {
        ResponseEditarAllProveedores rpt = new ResponseEditarAllProveedores();
        String SQL = "{ call COMPRAS.sp_EditarProveedores(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Proveedores actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Proveedores.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarProveedores", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoProveedores EditarEstadoProveedores(RequestEditarEstadoProveedores request, int estado) {
        ResponseEditarEstadoProveedores rpt = new ResponseEditarEstadoProveedores();
        String SQL = "{ call COMPRAS.sp_EditarProveedores_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdProveedores());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Proveedores actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Proveedores.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarProveedores_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
