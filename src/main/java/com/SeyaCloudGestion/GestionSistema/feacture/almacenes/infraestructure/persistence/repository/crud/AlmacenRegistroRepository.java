package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestRegistroAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseRegistroAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces.IAlmacenRegistro;
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
public class AlmacenRegistroRepository implements IAlmacenRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroAlmacen RegistroAlmacen(RequestRegistroAlmacen request) {
        ResponseRegistroAlmacen rpt = new ResponseRegistroAlmacen();
        String SQL = "{ call INVENTARIO.sp_RegistroAlmacen(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            setParameter(pstmt, 2, request.getIdSucursal());
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Almacenes insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Almacenes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un almacen con esa descripción en esta sucursal.");
            } else {
                rpt.setMessage("Error al registrar el almacen.");
            }
            log.error("Error en ALMACEN.sp_RegistroAlmacenes", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
