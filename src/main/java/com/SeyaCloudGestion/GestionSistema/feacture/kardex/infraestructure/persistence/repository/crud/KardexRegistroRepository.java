package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestRegistroKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseRegistroKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces.IKardexRegistro;
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
public class KardexRegistroRepository implements IKardexRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroKardex RegistroKardex(RequestRegistroKardex request) {
        ResponseRegistroKardex rpt = new ResponseRegistroKardex();
        String SQL = "{ call INVENTARIO.sp_RegistrarMovimientoKardex(?,?,?,?,?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId = 1L;
            Long sucursalId = 1L;
            Long almacenId = 1L;

            pstmt.setLong(1, empresaId);
            pstmt.setLong(2, sucursalId);
            pstmt.setLong(3, almacenId);
            pstmt.setLong(4, request.getIdArticulo());
            pstmt.setString(5, request.getTipoMovimiento());
            setParameter(pstmt, 6, request.getCantidadEntrada() );
            setParameter(pstmt, 7, request.getCostoEntrada() );
            setParameter(pstmt, 8, request.getCantidadSalida() );
            setParameter(pstmt, 9, request.getCostoSalida() );
            pstmt.setDouble(10, request.getSaldoCantidad());
            pstmt.setDouble(11, request.getSaldoCosto());
            pstmt.setLong(12, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Kardex insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Kardex.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_RegistrarMovimientoKardex", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
