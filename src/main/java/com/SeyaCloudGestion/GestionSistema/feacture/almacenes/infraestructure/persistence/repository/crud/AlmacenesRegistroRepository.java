package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestRegistroAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseRegistroAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces.IAlmacenesRegistro;
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
public class AlmacenesRegistroRepository implements IAlmacenesRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroAlmacenes RegistroAlmacenes(RequestRegistroAlmacenes request) {
        ResponseRegistroAlmacenes rpt = new ResponseRegistroAlmacenes();
        String SQL = "{ call INVENTARIO.sp_RegistroAlmacenes(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            setParameter(pstmt, 2, request.getIdSucursales());
            Long userId = 1L;
            pstmt.setLong(3, userId);

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
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_RegistroAlmacenes", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
