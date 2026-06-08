package com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestEditarAllCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestEditarEstadoCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseEditarAllCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseEditarEstadoCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces.ICompraEdicion;
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
public class CompraEdicionRepository implements ICompraEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllCompra EditarAllCompra(RequestEditarAllCompra request) {
        ResponseEditarAllCompra rpt = new ResponseEditarAllCompra();
        String SQL = "{ call COMPRAS.sp_EditarCompra(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Compra actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Compra.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarCompra", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoCompra EditarEstadoCompra(RequestEditarEstadoCompra request, int estado) {
        ResponseEditarEstadoCompra rpt = new ResponseEditarEstadoCompra();
        String SQL = "{ call COMPRAS.sp_EditarCompra_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Compra actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Compra.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_EditarCompra_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
