package com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestRegistroVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseRegistroVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces.IVentaRegistro;
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
public class VentaRegistroRepository implements IVentaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroVenta RegistroVenta(RequestRegistroVenta request) {
        ResponseRegistroVenta rpt = new ResponseRegistroVenta();
        String SQL = "{ call VENTAS.sp_RegistroVenta(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Venta insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Venta.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroVenta", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
