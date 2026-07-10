package com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces.ISotckEdicion;
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
public class SotckEdicionRepository implements ISotckEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllSotck EditarAllSotck(RequestEditarAllSotck request) {
        ResponseEditarAllSotck rpt = new ResponseEditarAllSotck();

        String SQL = "{ call INVENTARIO.sp_EditarStockProducto(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(1, request.getIdStockArticulo());
            pstmt.setDouble(2, request.getStock());
            pstmt.setLong(3, sucursalId);
            pstmt.setLong(4, empresaId);
            pstmt.setLong(5, request.getIdAlmacen());
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("stock editado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se edito el stock.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage("Error al actualizar el stock.");
            log.error("Error en INVENTARIO.sp_EditarStockProducto", e);
        }

        return rpt;
    }
}