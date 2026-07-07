package com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.repository.crud;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestRegistroSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.ResponseRegistroSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.interfaces.ISotckRegistro;
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
public class SotckRegistroRepository  implements ISotckRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroSotck RegistroSotck(RequestRegistroSotck request) {
        ResponseRegistroSotck rpt = new ResponseRegistroSotck();

        String SQL = "{ call INVENTARIO.sp_RegistroStockProducto(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId = 1L;
            Long sucursalId =1L;
            pstmt.setLong(1, request.getIdProducto());
            pstmt.setLong(2, request.getIdAlmacen());
            pstmt.setLong(3, sucursalId);
            pstmt.setLong(4, empresaId);
            pstmt.setDouble(5,request.getStock());
            pstmt.setLong(6, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("stock insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el stock.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe este un stock de este producto en este alamcen.");
            } else {
                rpt.setMessage("Error al registrar el stock.");
            }
            log.error("Error en INVENTARIO.sp_RegistroStockProducto", e);
        }
        return rpt;
    }
}