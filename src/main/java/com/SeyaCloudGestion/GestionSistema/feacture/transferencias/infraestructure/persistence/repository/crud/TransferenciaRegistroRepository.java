package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces.ITransferenciaRegistro;
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
public class TransferenciaRegistroRepository implements ITransferenciaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTransferencia RegistroTransferencia(RequestRegistroTransferencia request) {
        ResponseRegistroTransferencia rpt = new ResponseRegistroTransferencia();
        String SQL = "{ call ALMACEN.sp_RegistroTransferencia(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Transferencia insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Transferencia.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_RegistroTransferencia", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
