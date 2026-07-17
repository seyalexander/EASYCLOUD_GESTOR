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
import java.sql.ResultSet;
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
        String SQL = "{ call INVENTARIO.sp_RegistrarTransferencia(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long sucursalId = 1L;
            Long empresaId = 1L;
            pstmt.setLong(1, request.getIdAlmacenOrigen());
            pstmt.setLong(2, request.getIdAlmacenDestino());
            pstmt.setLong(3, empresaId);
            pstmt.setLong(4, sucursalId);
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    rpt.setIdTransferenciaCabecera(rs.getLong("idTransferencia"));
                    rpt.setExito(true);
                    rpt.setMessage("Transferencia insertado correctamente.");
                } else {
                    rpt.setExito(false);
                    rpt.setMessage("No se pudo obtener el ID de la transferencia.");
                }
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_RegistrarTransferencia", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
