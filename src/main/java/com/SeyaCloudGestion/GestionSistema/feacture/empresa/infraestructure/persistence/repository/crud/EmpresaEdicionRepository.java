package com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.repository.crud;


import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarAllEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseEditarAllEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseEditarEstadoEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces.IEmpresaEdicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
@Transactional("sqlServerTransactionManager")
public class EmpresaEdicionRepository implements IEmpresaEdicion {
    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseEditarEstadoEmpresa editarEstadoEmpresa(RequestEditarEstadoEmpresa request, int estado, long userAutenticado) {
        ResponseEditarEstadoEmpresa rpt = new ResponseEditarEstadoEmpresa();

        String SQL = "{ call CONFIGURACION.sp_EditarEmpresa_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdEmpresa());
            pstmt.setInt(2, estado);
            pstmt.setLong(3, userAutenticado);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Empresa actualizada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó la empresa.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }

    @Override
    public ResponseEditarAllEmpresa editarAllEmpresa(RequestEditarAllEmpresa request, long userAutenticado) {
        ResponseEditarAllEmpresa rpt = new ResponseEditarAllEmpresa();

        String SQL = "{ call CONFIGURACION.sp_EditarEmpresa(?,?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdEmpresa());
            pstmt.setString(2, request.getImagenUrl());
            pstmt.setString(3, request.getRazonSocial());
            pstmt.setString(4, request.getRuc());
            pstmt.setString(5, request.getDireccion());
            pstmt.setString(6, request.getTelefono());
            pstmt.setString(7, request.getEmail());
            pstmt.setString(8, request.getLogoUrl());
            pstmt.setInt(9, request.getEstado());
            pstmt.setLong(10, userAutenticado);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Empresa actualizada correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó la empresa.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
