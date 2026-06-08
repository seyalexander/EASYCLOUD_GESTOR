package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestRegistroUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseRegistroUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces.IUnidadMedidaRegistro;
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
public class UnidadMedidaRegistroRepository implements IUnidadMedidaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroUnidadMedida RegistroUnidadMedida(RequestRegistroUnidadMedida request) {
        ResponseRegistroUnidadMedida rpt = new ResponseRegistroUnidadMedida();
        String SQL = "{ call PRODUCTOS.sp_RegistroUnidadMedida(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            setParameter(pstmt, 1, request.getDescripcion());
            Long userId = 1L;
            pstmt.setLong(2, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("UnidadMedida insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó UnidadMedida.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_RegistroUnidadMedida", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
