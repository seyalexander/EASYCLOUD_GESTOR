package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces.ICodigoBarraEdicion;
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
public class CodigoBarraEdicionAllRepository implements ICodigoBarraEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllCodigoBarra EditarAllCodigoBarra(RequestEditarAllCodigoBarra request,long codigoBarra) {
        ResponseEditarAllCodigoBarra rpt = new ResponseEditarAllCodigoBarra();
        String SQL = "{ call PRODUCTOS.sp_EditarCodigoBarra(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1,request.getIdCodigoBarra());
            pstmt.setLong(2,codigoBarra);
            pstmt.setString(3,request.getCodigo());
            pstmt.setInt(4,request.getPrincipal());
            Long userId = 1L;
            pstmt.setLong(5, userId);
            Long empresaId = 1L;
            pstmt.setLong(6, empresaId);
            pstmt.execute();

            rpt.setExito(true);
            rpt.setMessage("CodigoBarra editado correctamente.");
            /*
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CodigoBarra actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó CodigoBarra.");
            }
             */

        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe este codigo en este Articulo");
            } else {
                rpt.setMessage("Error al actualizar el codigo.");
            }
            log.error("Error en PRODUCTOS.sp_EditarCodigoBarra", e);
        }
        return rpt;
    }
    /*
    @Override
    public ResponseEditarEstadoCodigoBarra EditarEstadoCodigoBarra(RequestEditarEstadoCodigoBarra request, int estado) {
        ResponseEditarEstadoCodigoBarra rpt = new ResponseEditarEstadoCodigoBarra();
        String SQL = "{ call PRODUCTOS.sp_EditarCodigoBarra_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CodigoBarra actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó CodigoBarra.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarCodigoBarra_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
     */
}
