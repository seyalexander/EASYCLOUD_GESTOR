package com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces.ICompraRegistro;
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
public class CompraRegistroRepository implements ICompraRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroCompra RegistroCompra(RequestRegistroCompra request,double subTotal, double igv, double total) {
        ResponseRegistroCompra rpt = new ResponseRegistroCompra();
        String SQL = "{ call COMPRAS.sp_RegistroCompra(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdProveedor());
            Long sucursalId = 1L;
            pstmt.setLong(2, sucursalId);
            pstmt.setLong(3, request.getIdAlmacen());
            pstmt.setLong(4, request.getIdTipoComprobante());
            pstmt.setString(5, request.getSerieComprobante());
            pstmt.setString(6, request.getNumeroComprobante());

            pstmt.setDouble(7, subTotal);
            pstmt.setDouble(8, igv);
            pstmt.setDouble(9, total);
            Long empresaId = 1L;
            Long usuarioId = 1L;
            pstmt.setLong(10, empresaId);
            pstmt.setLong(11, usuarioId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Compra insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Compra.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_RegistroCompra", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
