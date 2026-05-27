package com.SeyaCloudGestion.GestionSistema.feacture.guias.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestDetalleGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseDetalleGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.domain.interfaces.IGuiasIngresoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.infraestructure.persistence.model.GuiasIngresoModel;
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
public class GuiasIngresoDetalleRepository implements IGuiasIngresoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleGuiasIngreso DetalleGuiasIngreso(RequestDetalleGuiasIngreso request) {
        ResponseDetalleGuiasIngreso response = new ResponseDetalleGuiasIngreso();
        String SQL = "{ call COMPRAS.sp_ObtenerGuiasIngresoPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdGuiasIngreso());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    GuiasIngresoModel item = new GuiasIngresoModel();

                    response.setExito(true);
                    response.setMessage("GuiasIngreso obtenido correctamente.");
                    response.setGuiasIngreso(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró GuiasIngreso.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ObtenerGuiasIngresoPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
