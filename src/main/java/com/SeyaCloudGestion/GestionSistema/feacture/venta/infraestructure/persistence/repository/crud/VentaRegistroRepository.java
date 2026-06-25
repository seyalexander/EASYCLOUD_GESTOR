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
import java.sql.*;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class VentaRegistroRepository implements IVentaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroVenta RegistroVenta(RequestRegistroVenta request, double subTotal, double impuesto, double total) {
        ResponseRegistroVenta rpt = new ResponseRegistroVenta();
        String SQL = "{ call VENTAS.sp_RegistroVenta(?,?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            Long userId = 1L;
            Long empresaId = 1L;
            Long sucursalId = 1L;

            pstmt.setLong(1, request.getIdCliente());
            pstmt.setLong(2, userId);
            pstmt.setLong(3, sucursalId);
            pstmt.setLong(4, empresaId);
            pstmt.setLong(5, request.getIdTurnoCaja());

            pstmt.setString(6, request.getCondicionPago().name());

            pstmt.setDouble(7, subTotal);
            pstmt.setDouble(8, impuesto);
            pstmt.setDouble(9, total);

            pstmt.setLong(10, userId);


            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rpt.setExito(true);
                        rpt.setIdVenta(generatedKeys.getLong(1));
                        rpt.setMessage("Venta insertada correctamente.");
                    } else {
                        rpt.setExito(false);
                        rpt.setMessage("Venta insertada, pero no se pudo recuperar el ID generado.");
                    }
                }
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
