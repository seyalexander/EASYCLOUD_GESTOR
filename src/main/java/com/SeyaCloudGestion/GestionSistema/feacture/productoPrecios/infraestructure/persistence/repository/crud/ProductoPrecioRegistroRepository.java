package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.interfaces.IProductoPrecioRegistro;
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
public class ProductoPrecioRegistroRepository implements IProductoPrecioRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroProductoPrecio RegistroProductoPrecio(RequestRegistroProductoPrecio request) {
        ResponseRegistroProductoPrecio rpt = new ResponseRegistroProductoPrecio();
        String SQL = "{ call PRODUCTOS.sp_RegistroProductoPrecio(?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1,request.getIdArticulo());
            pstmt.setLong(2,request.getIdListaPrecio());
            pstmt.setDouble(3,request.getPrecio());
            setLocalDateTime(pstmt, 4, request.getFechaInicio());
            setLocalDateTimeOrNull(pstmt, 5, request.getFechaFin());
            Long userId = 1L;
            pstmt.setLong(6, userId);
            Long empresaId=1L;
            pstmt.setLong(7, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ProductoPrecio insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó ProductoPrecio.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe este precio en este producto con esa descripción.");
            } else {
                rpt.setMessage("Error al registrar el precio.");
            }
            log.error("Error en PRODUCTOS.sp_RegistroProductoPrecio", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
