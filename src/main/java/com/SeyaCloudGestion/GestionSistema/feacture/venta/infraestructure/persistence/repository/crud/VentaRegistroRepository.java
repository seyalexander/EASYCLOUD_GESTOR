package com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestRegistroVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseRegistroVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces.IVentaRegistro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.CallableStatement;
import javax.sql.DataSource;
import java.sql.*;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class VentaRegistroRepository implements IVentaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroVenta RegistroVenta(long idCaja,RequestRegistroVenta request, double subTotal, double impuesto, double total) {
        ResponseRegistroVenta rpt = new ResponseRegistroVenta();
        String SQL = "{ call VENTAS.sp_RegistroVenta(?,?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId = 1L;
            Long sucursalId = 1L;

            pstmt.setLong(1, request.getIdCliente());
            pstmt.setLong(2, userId);
            pstmt.setLong(3, sucursalId);
            pstmt.setLong(4, empresaId);
            pstmt.setLong(5, idCaja);

            pstmt.setString(6, request.getCondicionPago().name());

            pstmt.setDouble(7, subTotal);
            pstmt.setDouble(8, impuesto);
            pstmt.setDouble(9, total);

            pstmt.setLong(10, userId);


            int rowsAffected = pstmt.executeUpdate();

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    rpt.setIdVenta(rs.getLong("idVenta"));
                    rpt.setExito(true);
                    rpt.setMessage("Venta registrada correctamente.");
                } else {
                    rpt.setExito(false);
                    rpt.setMessage("No se pudo obtener el ID de la venta.");
                }
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
