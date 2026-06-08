package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.request.RequestRegistroCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response.ResponseRegistroCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.domain.interfaces.ICompraDetalleRegistro;
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
public class CompraDetalleRegistroRepository implements ICompraDetalleRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroCompraDetalle RegistroCompraDetalle(RequestRegistroCompraDetalle request) {
        ResponseRegistroCompraDetalle rpt = new ResponseRegistroCompraDetalle();
        String SQL = "{ call COMPRAS.sp_RegistroCompraDetalle(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CompraDetalle insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó CompraDetalle.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_RegistroCompraDetalle", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
