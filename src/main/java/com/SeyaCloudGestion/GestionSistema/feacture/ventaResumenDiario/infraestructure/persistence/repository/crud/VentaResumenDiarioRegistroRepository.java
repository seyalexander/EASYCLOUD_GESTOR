package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.request.RequestRegistroVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.response.ResponseRegistroVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.domain.interfaces.IVentaResumenDiarioRegistro;
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
public class VentaResumenDiarioRegistroRepository implements IVentaResumenDiarioRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroVentaResumenDiario RegistroVentaResumenDiario(RequestRegistroVentaResumenDiario request) {
        ResponseRegistroVentaResumenDiario rpt = new ResponseRegistroVentaResumenDiario();
        String SQL = "{ call VENTAS.sp_RegistroVentaResumenDiario(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdSucursal());
            setParameter(pstmt, 2, request.getMontoInical());
            setParameter(pstmt, 3, request.getTotalImpuestos());
            setParameter(pstmt, 4, request.getTotalNeto());
            Long userId = 1L;
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("VentaResumenDiario insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó VentaResumenDiario.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroVentaResumenDiario", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
