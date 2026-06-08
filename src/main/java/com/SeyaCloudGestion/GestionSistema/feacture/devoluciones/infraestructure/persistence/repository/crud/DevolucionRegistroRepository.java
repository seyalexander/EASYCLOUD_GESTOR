package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestRegistroDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseRegistroDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces.IDevolucionRegistro;
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
public class DevolucionRegistroRepository implements IDevolucionRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroDevolucion RegistroDevolucion(RequestRegistroDevolucion request) {
        ResponseRegistroDevolucion rpt = new ResponseRegistroDevolucion();
        String SQL = "{ call VENTAS.sp_RegistroDevolucion(?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdVenta());
            setParameter(pstmt, 2, request.getIdArticulo());
            setParameter(pstmt, 3, request.getCantidad());
            setParameter(pstmt, 4, request.getMotivo());
            setParameter(pstmt, 5, request.getFechaDevolucion());
            setParameter(pstmt, 6, request.getIdUsuario());
            setParameter(pstmt, 7, request.getFechaIngreso());
            Long userId = 1L;
            pstmt.setLong(8, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Devolucion insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Devolucion.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_RegistroDevolucion", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
