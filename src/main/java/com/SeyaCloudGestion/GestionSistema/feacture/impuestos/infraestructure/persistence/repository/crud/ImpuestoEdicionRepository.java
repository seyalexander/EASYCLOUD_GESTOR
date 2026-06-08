package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestEditarAllImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestEditarEstadoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseEditarAllImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseEditarEstadoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces.IImpuestoEdicion;
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
public class ImpuestoEdicionRepository implements IImpuestoEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllImpuesto EditarAllImpuesto(RequestEditarAllImpuesto request) {
        ResponseEditarAllImpuesto rpt = new ResponseEditarAllImpuesto();
        String SQL = "{ call dbo.sp_EditarImpuesto(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);
            pstmt.setLong(2, request.getIdImpuesto());
            pstmt.setString(3, request.getDescripcion());
            pstmt.setDouble(4, request.getPorcentaje());
            pstmt.setInt(5, request.getEsPrincipal());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Impuesto actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Impuesto.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_EditarImpuesto", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoImpuesto EditarEstadoImpuesto(RequestEditarEstadoImpuesto request, int estado) {
        ResponseEditarEstadoImpuesto rpt = new ResponseEditarEstadoImpuesto();
        String SQL = "{ call dbo.sp_EditarImpuesto_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, estado);
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Impuesto actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Impuesto.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_EditarImpuesto_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
