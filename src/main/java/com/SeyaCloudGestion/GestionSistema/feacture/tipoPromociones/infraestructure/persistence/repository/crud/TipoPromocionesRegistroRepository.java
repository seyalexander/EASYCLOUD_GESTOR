package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.request.RequestRegistroTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response.ResponseRegistroTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.domain.interfaces.ITipoPromocionesRegistro;
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
public class TipoPromocionesRegistroRepository implements ITipoPromocionesRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTipoPromociones RegistroTipoPromociones(RequestRegistroTipoPromociones request) {
        ResponseRegistroTipoPromociones rpt = new ResponseRegistroTipoPromociones();
        String SQL = "{ call PRODUCTOS.sp_RegistroTipoPromociones(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            setParameter(pstmt, 2, request.getFechaIngreso());
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoPromociones insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó TipoPromociones.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_RegistroTipoPromociones", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
