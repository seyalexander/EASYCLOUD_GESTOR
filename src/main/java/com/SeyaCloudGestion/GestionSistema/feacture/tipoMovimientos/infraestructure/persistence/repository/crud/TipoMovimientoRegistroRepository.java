package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestRegistroTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseRegistroTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.interfaces.ITipoMovimientoRegistro;
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
public class TipoMovimientoRegistroRepository implements ITipoMovimientoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTipoMovimiento RegistroTipoMovimiento(RequestRegistroTipoMovimiento request) {
        ResponseRegistroTipoMovimiento rpt = new ResponseRegistroTipoMovimiento();
        String SQL = "{ call INVENTARIO.sp_RegistroTipoMovimiento(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            setParameter(pstmt, 2, request.getEsEntrada());
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);


            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoMovimiento insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó TipoMovimiento.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un tipo de movimiento con esa descripción.");
            } else {
                rpt.setMessage("Error al registrar el tipo movimiento.");
            }
            log.error("Error en INVENTARIO.sp_RegistroTipoMovimiento", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
