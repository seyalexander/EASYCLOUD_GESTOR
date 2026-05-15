package com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestRegistroEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.ResponseRegistroEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.interfaces.IEmpleadoRegistro;
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
public class EmpleadoRegistroRepository implements IEmpleadoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseRegistroEmpleado RegistroEmpleado(RequestRegistroEmpleado request, long userAutenticado, long idEmpresa) {
        ResponseRegistroEmpleado rpt = new ResponseRegistroEmpleado();

        String SQL = "{ call SEGURIDAD.sp_RegistroEmpleado(?,?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setString(1, request.getNombre());
            pstmt.setString(2, request.getApellido());
            pstmt.setString(3, request.getTelefono());
            pstmt.setString(4, request.getImagenUrl());
            pstmt.setString(5, request.getDocumento());
            pstmt.setLong(6, request.getIdTipoDocumento());
            pstmt.setString(7, request.getFechaNacimiento());
            pstmt.setString(8, request.getFechaIngreso());
            pstmt.setLong(9, userAutenticado);
            pstmt.setLong(10, idEmpresa);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Empleado insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el empleado.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
