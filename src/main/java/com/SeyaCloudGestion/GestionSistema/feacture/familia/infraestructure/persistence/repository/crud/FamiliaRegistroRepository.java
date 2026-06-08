package com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestRegistroFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseRegistroFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces.IFamiliaRegistro;
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
public class FamiliaRegistroRepository implements IFamiliaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroFamilia RegistroFamilia(RequestRegistroFamilia request) {
        ResponseRegistroFamilia rpt = new ResponseRegistroFamilia();

        String SQL = "{ call PRODUCTOS.sp_RegistroFamilia(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId = 1L;

            pstmt.setString(1, request.getDescripcion());
            pstmt.setString(2, request.getImagenUrl());
            pstmt.setLong(3, userId);
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Familia insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó la familia.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);

            String mensaje = e.getMessage();

            if (mensaje != null && mensaje.contains("UQ_Familia_Empresa")) {
                rpt.setMessage("Ya existe una familia con esa descripción.");
            }
            else {
                rpt.setMessage("Error al registrar la familia.");
            }
        }

        return rpt;
    }

}
