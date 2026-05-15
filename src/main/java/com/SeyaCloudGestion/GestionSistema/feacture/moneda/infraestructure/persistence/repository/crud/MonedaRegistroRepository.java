package com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestRegistroMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseRegistroMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces.IMonedaRegistro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class MonedaRegistroRepository implements IMonedaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseRegistroMoneda RegistrarMoneda(RequestRegistroMoneda request, long userAutenticado) {

        ResponseRegistroMoneda rpt = new ResponseRegistroMoneda();
        String SQL = "{ call CONFIGURACION.sp_RegistroMoneda(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setString(1, request.getDescripcion());
            pstmt.setString(2, request.getSimbolo());
            pstmt.setInt(3, request.getEsPrincipal());
            pstmt.setLong(4, userAutenticado);

            boolean hasResult = pstmt.execute();

            if (hasResult) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    if (rs != null && rs.next()) {
                        rpt.setExito(rs.getInt("exito") == 1);
                        rpt.setMessage(rs.getString("mensaje"));
                        return rpt;
                    }
                }
            }

            rpt.setExito(true);
            rpt.setMessage("Moneda registrada correctamente.");

        } catch (SQLException e) {
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("UNIQUE KEY")) {
                ResponseRegistroMoneda response = new ResponseRegistroMoneda();
                response.setExito(false);
                response.setMessage("La moneda ya existe en el sistema.");
                return response;
            }

            rpt.setExito(false);
            rpt.setMessage("Error al registrar moneda: " + e.getMessage());
        }

        return rpt;
    }
}
