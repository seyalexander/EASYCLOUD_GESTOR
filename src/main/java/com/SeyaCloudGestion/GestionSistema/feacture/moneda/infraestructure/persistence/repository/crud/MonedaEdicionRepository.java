package com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarEstadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarPredeterminadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarEstadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseEditarPredeterminadoMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces.IMonedaEdicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Transactional("sqlServerTransactionManager")
public class MonedaEdicionRepository implements IMonedaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseEditarAllMoneda EditarAllMoneda(RequestEditarAllMoneda request, long userAutenticado) {
        ResponseEditarAllMoneda rpt = new ResponseEditarAllMoneda();

        String SQL = "{ call CONFIGURACION.sp_EditarMoneda(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdMoneda());
            pstmt.setString(2, request.getDescripcion());
            pstmt.setString(3, request.getSimbolo());
            pstmt.setInt(4, request.getEsPrincipal());
            pstmt.setInt(5, request.getEstado());
            pstmt.setLong(6, userAutenticado);

            boolean hasResult = pstmt.execute();

            if (hasResult) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    if (rs != null && rs.next()) {
                        rpt.setExito(rs.getInt("exito") == 1);
                        rpt.setMessage(rs.getString("mensaje"));
                        return rpt;
                    }
                }
            }

            rpt.setExito(true);
            rpt.setMessage("Moneda actualizada correctamente.");

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }

    @Override
    public ResponseEditarEstadoMoneda EditarEstadoMoneda(RequestEditarEstadoMoneda request, int estado, long userAutenticado) {
        ResponseEditarEstadoMoneda rpt = new ResponseEditarEstadoMoneda();

        String SQL = "{ call CONFIGURACION.sp_EditarMoneda_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdMoneda());
            pstmt.setInt(2, estado);
            pstmt.setLong(3, userAutenticado);

            boolean hasResult = pstmt.execute();

            if (hasResult) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    if (rs != null && rs.next()) {
                        rpt.setExito(rs.getInt("exito") == 1);
                        rpt.setMessage(rs.getString("mensaje"));
                        return rpt;
                    }
                }
            }

            rpt.setExito(true);
            rpt.setMessage("Moneda actualizada correctamente.");

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }

    @Override
    public ResponseEditarPredeterminadoMoneda EditarPredetermiandoMoneda(RequestEditarPredeterminadoMoneda request, long userAutenticado, long empresaAutenticado) {
        ResponseEditarPredeterminadoMoneda rpt = new ResponseEditarPredeterminadoMoneda();

        String SQL = "{ call CONFIGURACION.sp_CambiarMonedaPredeterminada(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdMoneda());
            pstmt.setLong(2, userAutenticado);
            pstmt.setLong(3, empresaAutenticado);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Moneda actualizada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó la Moneda.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
