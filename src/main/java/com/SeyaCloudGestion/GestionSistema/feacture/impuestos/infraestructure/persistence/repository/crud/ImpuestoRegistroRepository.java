package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestRegistroImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseRegistroImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces.IImpuestoRegistro;
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
public class ImpuestoRegistroRepository implements IImpuestoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroImpuesto RegistroImpuesto(RequestRegistroImpuesto request) {
        ResponseRegistroImpuesto rpt = new ResponseRegistroImpuesto();
        String SQL = "{ call dbo.sp_RegistroImpuesto(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            setParameter(pstmt, 2, request.getPorcentaje());
            setParameter(pstmt, 3, request.getEsPrincipal());
            //Long userId = 1L;
           // pstmt.setLong(4, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Impuesto insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Impuesto.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_RegistroImpuesto", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
