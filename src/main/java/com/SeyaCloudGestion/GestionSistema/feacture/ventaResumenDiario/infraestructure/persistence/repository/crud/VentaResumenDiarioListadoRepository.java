package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.request.RequestListaVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.response.ResponseListaVentaResumenDiario;
import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.domain.interfaces.IVentaResumenDiarioListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class VentaResumenDiarioListadoRepository implements IVentaResumenDiarioListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaVentaResumenDiario listaVentaResumenDiario(RequestListaVentaResumenDiario request) {
        ResponseListaVentaResumenDiario rpt = new ResponseListaVentaResumenDiario();
        List<VentaResumenDiarioModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarVentaResumenDiario(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    VentaResumenDiarioModel item = new VentaResumenDiarioModel();
                item.setIdVentaresumendiario(rs.getLong("idVentaresumendiario"));
                item.setIdSucursal(rs.getLong("idSucursal"));
                item.setFecha((rs.getTimestamp("fecha") != null ? rs.getTimestamp("fecha").toLocalDateTime() : null));
                item.setMontoInical(rs.getDouble("montoInical"));
                item.setTotalImpuestos(rs.getDouble("totalImpuestos"));
                item.setTotalNeto(rs.getDouble("totalNeto"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setVentaResumenDiarios(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarVentaResumenDiario", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
