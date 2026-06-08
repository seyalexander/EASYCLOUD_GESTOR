package com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestRegistroMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseRegistroMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaRegistro;
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
public class MarcaRegistroRepository implements IMarcaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroMarca RegistroMarca(RequestRegistroMarca request) {
        ResponseRegistroMarca rpt = new ResponseRegistroMarca();
        String SQL = "{ call PRODUCTOS.sp_RegistroMarca(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            setParameter(pstmt, 1, request.getDescripcion());
            setParameter(pstmt, 2, request.getImagenUrl());
            Long userId = 1L;
            Long empresaId = 1L;
            pstmt.setLong(3, userId);
            pstmt.setLong(4, empresaId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Marca insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Marca.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_RegistroMarca", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
