package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestEditarAllProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestEditarEstadoProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseEditarAllProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseEditarEstadoProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.interfaces.IProductoPrecioEdicion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import static com.SeyaCloudGestion.GestionSistema.common.sqlParametersDate.SqlParameterDate.setLocalDateTime;
import static com.SeyaCloudGestion.GestionSistema.common.sqlParametersDate.SqlParameterDate.setLocalDateTimeOrNull;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ProductoPrecioEdicionRepository implements IProductoPrecioEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllProductoPrecio EditarAllProductoPrecio(RequestEditarAllProductoPrecio request) {
        ResponseEditarAllProductoPrecio rpt = new ResponseEditarAllProductoPrecio();
        String SQL = "{ call PRODUCTOS.sp_EditarProductoPrecio(?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1,request.getIdProductoPrecio());
            pstmt.setLong(2,request.getIdArticulo());
            pstmt.setLong(3,request.getIdListaPrecio());
            pstmt.setDouble(4,request.getPrecio());
            setLocalDateTime(pstmt, 5, request.getFechaInicio());
            setLocalDateTimeOrNull(pstmt, 6, request.getFechaFin());
            pstmt.setInt(7,request.getEstado());
            Long userId = 1L;
            pstmt.setLong(8, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ProductoPrecio actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó ProductoPrecio.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarProductoPrecio", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoProductoPrecio EditarEstadoProductoPrecio(RequestEditarEstadoProductoPrecio request, int estado) {
        ResponseEditarEstadoProductoPrecio rpt = new ResponseEditarEstadoProductoPrecio();
        String SQL = "{ call PRODUCTOS.sp_EditarProductoPrecio_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1,request.getIdProductoPrecio());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ProductoPrecio actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó ProductoPrecio.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarProductoPrecio_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
