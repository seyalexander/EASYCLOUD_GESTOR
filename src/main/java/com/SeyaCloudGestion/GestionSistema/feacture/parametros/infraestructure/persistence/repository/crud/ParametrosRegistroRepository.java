package com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestRegistroParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseRegistroParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces.IParametrosRegistro;
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
public class ParametrosRegistroRepository implements IParametrosRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroParametros RegistroParametros(RequestRegistroParametros request) {
        ResponseRegistroParametros rpt = new ResponseRegistroParametros();
        String SQL = "{ call CONFIGURACION.sp_RegistroParametroSistema(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getClave());
            setParameter(pstmt, 2, request.getValor());
            setParameter(pstmt, 3, request.getDescripcion());
            //Long userId = 1L;
            //pstmt.setLong(4, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Parametros insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Parametros.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_RegistroParametroSistema", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
