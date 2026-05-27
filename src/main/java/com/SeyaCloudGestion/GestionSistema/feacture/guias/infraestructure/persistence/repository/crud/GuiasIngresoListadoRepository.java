package com.SeyaCloudGestion.GestionSistema.feacture.guias.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestListaGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseListaGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.domain.interfaces.IGuiasIngresoListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class GuiasIngresoListadoRepository implements IGuiasIngresoListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaGuiasIngreso listaGuiasIngreso(RequestListaGuiasIngreso request) {
        ResponseListaGuiasIngreso rpt = new ResponseListaGuiasIngreso();
        List<GuiasIngresoModel> registros = new ArrayList<>();
        String SQL = "{ call COMPRAS.sp_ListarGuiasIngreso(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    GuiasIngresoModel item = new GuiasIngresoModel();

                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setGuiasIngresos(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ListarGuiasIngreso", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
