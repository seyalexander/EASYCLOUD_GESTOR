package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestAjustarInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestConteoFisicoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseAjustarInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseConteoFisicoInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces.IInventarioEdicion;
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
public class InventarioEdicionRepository implements IInventarioEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseConteoFisicoInventario ConteoFisicoInventario(RequestConteoFisicoInventario request) {
        ResponseConteoFisicoInventario rpt = new ResponseConteoFisicoInventario();
        String SQL = "{ call INVENTARIO.sp_ConteoFisicoInventario(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            Long empresaId = 1L;
            Long sucursalId = 1L;

            pstmt.setLong(1, empresaId);
            pstmt.setLong( 2, sucursalId);
            pstmt.setLong(3, request.getIdInventarioCabecera());
            Long userId = 1L;
            pstmt.setLong(4, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Inventario Cerrado correctamente.");;
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Inventario.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ConteoFisicoInventario", e);
        }
        return rpt;
    }

    @Override
    public ResponseAjustarInventario AjusteInventario(RequestAjustarInventario request) {
        ResponseAjustarInventario rpt = new ResponseAjustarInventario();

        String SQL = "{ call INVENTARIO.sp_AjustarInventario(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId = 1L;
            Long sucursalId = 1L;
            Long userId = 1L;

            pstmt.setLong(1, empresaId);
            pstmt.setLong(2, sucursalId);
            pstmt.setLong(3, request.getIdInventarioCabecera());
            pstmt.setLong(4, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Ajuste de inventario realizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se realizó el ajuste de inventario.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_AjustarInventario", e);
        }

        return rpt;
    }

}
