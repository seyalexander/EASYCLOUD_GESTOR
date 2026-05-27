package com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestRegistroEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseRegistroEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces.IEmpresaRegistro;
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
public class EmpresaRegistroRepository implements IEmpresaRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroEmpresa registrarEmpresa(RequestRegistroEmpresa request, long userAutenticado) {
        ResponseRegistroEmpresa rpt = new ResponseRegistroEmpresa();

        String SQL = "{ call CONFIGURACION.sp_RegistroEmpresa(?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setString(1, request.getImagenUrl());
            pstmt.setString(2, request.getRazonSocial());
            pstmt.setString(3, request.getRuc());
            pstmt.setString(4, request.getDireccion());
            pstmt.setString(5, request.getTelefono());
            pstmt.setString(6, request.getEmail());
            pstmt.setString(7, request.getLogoUrl());
            pstmt.setLong(8, userAutenticado);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Empresa insertada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó la Empresa.");
            }

        } catch (SQLException e) {
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("UNIQUE KEY")) {
                ResponseRegistroEmpresa response = new ResponseRegistroEmpresa();
                response.setExito(false);
                response.setMessage("El RUC ya está registrado.");
                return response;
            }

            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
