package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.request.RequestDetalleVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.response.ResponseDetalleVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.domain.interfaces.IVentaResumenDiarioDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.model.VentaResumenDiarioModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class VentaResumenDiarioDetalleRepository implements IVentaResumenDiarioDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleVentaResumenDiario DetalleVentaResumenDiario(RequestDetalleVentaResumenDiario request) {
        ResponseDetalleVentaResumenDiario response = new ResponseDetalleVentaResumenDiario();
        String SQL = "{ call VENTAS.sp_ObtenerVentaResumenDiarioPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdVentaResumenDiario());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    VentaResumenDiarioModel item = new VentaResumenDiarioModel();
                    item.setIdVentaresumendiario(rs.getLong("idVentaresumendiario"));
                    item.setIdSucursal(rs.getLong("idSucursal"));
                    item.setFecha((rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null));
                    item.setMontoInical(rs.getDouble("montoInical"));
                    item.setTotalImpuestos(rs.getDouble("totalImpuestos"));
                    item.setTotalNeto(rs.getDouble("totalNeto"));
                    response.setExito(true);
                    response.setMessage("VentaResumenDiario obtenido correctamente.");
                    response.setVentaResumenDiario(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró VentaResumenDiario.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ObtenerVentaResumenDiarioPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
