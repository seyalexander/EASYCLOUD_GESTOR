package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestRegistroInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseRegistroInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces.IInventarioRegistro;
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
public class InventarioRegistroRepository implements IInventarioRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroInventario RegistroInventario(RequestRegistroInventario request) {
        ResponseRegistroInventario rpt = new ResponseRegistroInventario();
        String SQL = "{ call INVENTARIO.sp_RegistroInventarioCabecera(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId = 1L;
            Long sucursalId = 1L;

            pstmt.setLong(1, empresaId);
            pstmt.setLong(2, sucursalId);
            pstmt.setLong(3, request.getIdAlmacen());
            pstmt.setString(4, request.getObservacion());
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage(" Cabecera Inventario insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó la Cabecera Inventario.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_RegistroInventarioCabecera", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
