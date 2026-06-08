package com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestRegistroPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseRegistroPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.domain.interfaces.IPromocionesRegistro;
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
public class PromocionesRegistroRepository implements IPromocionesRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroPromociones RegistroPromociones(RequestRegistroPromociones request) {
        ResponseRegistroPromociones rpt = new ResponseRegistroPromociones();
        String SQL = "{ call PRODUCTOS.sp_RegistroPromociones(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getDescripcion());
            setParameter(pstmt, 2, request.getIdTipoPromocion());
            setParameter(pstmt, 3, request.getFechaInicio());
            setParameter(pstmt, 4, request.getFechaFin());
            Long userId = 1L;
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Promociones insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Promociones.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_RegistroPromociones", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
