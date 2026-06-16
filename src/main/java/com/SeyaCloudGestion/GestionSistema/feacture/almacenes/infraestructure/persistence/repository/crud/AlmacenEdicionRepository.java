package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarAllAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarEstadoAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarAllAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarEstadoAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces.IAlmacenEdicion;
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
public class AlmacenEdicionRepository implements IAlmacenEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllAlmacen EditarAllAlmacen(RequestEditarAllAlmacen request) {
        ResponseEditarAllAlmacen rpt = new ResponseEditarAllAlmacen();
        String SQL = "{ call INVENTARIO.sp_EditarAlmacen(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            setParameter(pstmt, 1, request.getIdAlmacen());
            setParameter(pstmt, 2, request.getDescripcion());
            pstmt.setInt(3, request.getEstado());
            Long userId = 1L;
            pstmt.setLong(4, userId);
            Long empresaId = 1L;
            pstmt.setLong(5, empresaId);
            Long sucursalId = 1L;
            pstmt.setLong(6, sucursalId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Almacenes actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Almacenes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un almacen con esa descripción en esta sucursal.");
            } else {
                rpt.setMessage("Error al actualizar el almacen.");
            }
            log.error("Error en INVENTARIO.sp_EditarAlmacenes", e);
        }
        return rpt;
    }
    @Override
    public ResponseEditarEstadoAlmacen EditarEstadoAlmacen(RequestEditarEstadoAlmacen request, int estado) {
        ResponseEditarEstadoAlmacen rpt = new ResponseEditarEstadoAlmacen();

        String SQL = "{ call INVENTARIO.sp_EditarAlmacen_Estado(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId = 1L;
            Long sucursalId = 1L;

            pstmt.setLong(1, request.getIdAlmacen());
            pstmt.setInt(2, estado);
            pstmt.setLong(3, userId);
            pstmt.setLong(4, empresaId);
            pstmt.setLong(5, sucursalId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Estado del almacén actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se encontró el almacén o no pertenece a su sucursal/empresa.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage("Error al cambiar el estado del almacén: " + e.getMessage());
            log.error("Error en INVENTARIO.sp_EditarAlmacen_Estado", e);
        }

        return rpt;
    }
    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
