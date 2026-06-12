package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestRegistroSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseRegistroSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces.ISucursalesRegistro;
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
public class SucursalesRegistroRepository implements ISucursalesRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroSucursales RegistroSucursales(RequestRegistroSucursales request) {
        ResponseRegistroSucursales rpt = new ResponseRegistroSucursales();
        String SQL = "{ call INVENTARIO.sp_RegistroSucursales(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            Long userId = 1L;
            pstmt.setLong(2, userId);
            Long empresaId = 1L;
            pstmt.setLong(3, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Sucursales insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Sucursales.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe una sucursal con esa descripción.");
            } else {
                rpt.setMessage("Error al registrar la sucursal.");
            }
            log.error("Error en INVENTARIO.sp_RegistroSucursales", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
