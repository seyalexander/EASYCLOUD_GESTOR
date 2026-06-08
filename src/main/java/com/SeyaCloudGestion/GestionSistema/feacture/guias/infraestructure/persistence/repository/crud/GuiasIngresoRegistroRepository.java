package com.SeyaCloudGestion.GestionSistema.feacture.guias.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.request.RequestRegistroGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.application.dto.response.ResponseRegistroGuiasIngreso;
import com.SeyaCloudGestion.GestionSistema.feacture.guias.domain.interfaces.IGuiasIngresoRegistro;
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
public class GuiasIngresoRegistroRepository implements IGuiasIngresoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroGuiasIngreso RegistroGuiasIngreso(RequestRegistroGuiasIngreso request) {
        ResponseRegistroGuiasIngreso rpt = new ResponseRegistroGuiasIngreso();
        String SQL = "{ call COMPRAS.sp_RegistroGuiasIngreso(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("GuiasIngreso insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó GuiasIngreso.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_RegistroGuiasIngreso", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
