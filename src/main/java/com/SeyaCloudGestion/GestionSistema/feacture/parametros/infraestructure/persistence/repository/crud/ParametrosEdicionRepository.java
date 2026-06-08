package com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestEditarAllParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestEditarEstadoParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseEditarAllParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseEditarEstadoParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces.IParametrosEdicion;
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
public class ParametrosEdicionRepository implements IParametrosEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllParametros EditarAllParametros(RequestEditarAllParametros request) {
        ResponseEditarAllParametros rpt = new ResponseEditarAllParametros();
        String SQL = "{ call CONFIGURACION.sp_EditarParametroSistema(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1, request.getIdParametros());
            pstmt.setString(2, request.getClave());
            pstmt.setString(3, request.getValor());
            pstmt.setString(4, request.getDescripcion());
            pstmt.setInt(5, request.getEstado());
            //Long userId = 1L;
            //pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Parametros actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Parametros.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_EditarParametros", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoParametros EditarEstadoParametros(RequestEditarEstadoParametros request, int estado) {
        ResponseEditarEstadoParametros rpt = new ResponseEditarEstadoParametros();
        String SQL = "{ call CONFIGURACION.sp_EditarParametroSistema_Estado(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdParametros());
            pstmt.setInt(2, estado);
            //Long userId = 1L;
            //pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Parametros actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Parametros.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_EditarParametros_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
