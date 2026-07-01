package com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestEditarAllCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestAnularCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseEditarAllCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseAnularCompra;
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
        String SQL = "{ call COMPRAS.sp_EditarCompra(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdCompra());
            pstmt.setLong(2, request.getIdTipoComprobante());
            pstmt.setString(3, request.getSerieComprobante());
            pstmt.setString(4, request.getNumeroComprobante());
            pstmt.setDouble(5, request.getSubTotal());
            pstmt.setDouble(6, request.getImpuesto());
            pstmt.setDouble(7, request.getTotal());

            Long empresaId = 1L;
            Long sucursalId = 1L;
            Long usuarioId = 1L;
            pstmt.setLong(8, empresaId);
            pstmt.setLong(9, sucursalId);
            pstmt.setLong(10, usuarioId);

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
    public ResponseAnularCompra AnularCompra(RequestAnularCompra request, int estado) {
        ResponseAnularCompra rpt = new ResponseAnularCompra();
        String SQL = "{ call COMPRAS.sp_EditarCompra_Estado(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1, request.getIdCompra());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(4, empresaId);
            pstmt.setLong(5, sucursalId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Compra anulada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se anulo la  Compra.");
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
