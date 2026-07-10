package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestRegistroProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.ResponseRegistroProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.interfaces.IProductoImpuestoRegistro;
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
public class ProductoImpuestoRegistroRepository implements IProductoImpuestoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroProductoImpuesto RegistroProductoImpuesto(RequestRegistroProductoImpuesto request) {
        ResponseRegistroProductoImpuesto rpt = new ResponseRegistroProductoImpuesto();
        String SQL = "{ call PRODUCTOS.sp_RegistroProductoImpuesto(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdArticulo());
            setParameter(pstmt, 2, request.getPorcentaje());
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ProductoImpuesto insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó ProductoImpuesto.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un impuesto en este articulo.");
            } else {
                rpt.setMessage("Error al registrar el impuesto del articulo.");
            }
            log.error("Error en PRODUCTOS.sp_RegistroProductoImpuesto", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
