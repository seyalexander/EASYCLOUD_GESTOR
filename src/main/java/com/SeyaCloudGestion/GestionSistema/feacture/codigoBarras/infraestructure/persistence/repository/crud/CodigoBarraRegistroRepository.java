package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseRegistroCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces.ICodigoBarraRegistro;
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
public class CodigoBarraRegistroRepository implements ICodigoBarraRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroCodigoBarra RegistroCodigoBarra(RequestRegistroCodigoBarra request) {
        ResponseRegistroCodigoBarra rpt = new ResponseRegistroCodigoBarra();
        String SQL = "{ call PRODUCTOS.sp_RegistroCodigoBarra(?,?,?,?) }";

        try (Connection conn = con.getConnection();

             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdArticulo());
            setParameter(pstmt, 2, request.getCodigo());
            setParameter(pstmt, 3, request.getPrincipal());
            Long userId = 1L;
            pstmt.setLong(4, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("CodigoBarra insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó CodigoBarra.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_RegistroCodigoBarra", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
