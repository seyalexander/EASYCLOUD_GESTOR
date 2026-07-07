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
import java.sql.*;

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
            pstmt.setLong(2, request.getIdAlmacen());
            pstmt.setLong(3, request.getIdTipoComprobante());
            pstmt.setString(4, request.getSerieComprobante());
            pstmt.setString(5, request.getNumeroComprobante());

            pstmt.setDouble(6, subTotal);
            pstmt.setDouble(7, igv);
            pstmt.setDouble(8, total);
            Long empresaId = 1L;
            Long usuarioId = 1L;
            pstmt.setLong(9, empresaId);
            Long sucursalId = 1L;
            pstmt.setLong(10, sucursalId);
            pstmt.setLong(11, usuarioId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    rpt.setIdCompra(rs.getLong("idCompra"));
                    rpt.setExito(true);
                    rpt.setMessage("Compra registrada correctamente.");
                } else {
                    rpt.setExito(false);
                    rpt.setMessage("No se pudo obtener el ID de la compra.");
                }
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
