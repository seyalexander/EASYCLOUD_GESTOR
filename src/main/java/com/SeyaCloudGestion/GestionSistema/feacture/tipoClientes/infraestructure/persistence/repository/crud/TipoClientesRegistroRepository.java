package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestRegistroTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseRegistroTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces.ITipoClientesRegistro;
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
public class TipoClientesRegistroRepository implements ITipoClientesRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTipoClientes RegistroTipoClientes(RequestRegistroTipoClientes request) {
        ResponseRegistroTipoClientes rpt = new ResponseRegistroTipoClientes();
        String SQL = "{ call CLIENTES.sp_RegistroTipoCliente(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //Long userId = 1L;
            //pstmt.setLong(2, userId);
            pstmt.setString(1, request.getDescripcion());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("TipoClientes insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó TipoClientes.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_RegistroTipoClientes", e);
        }
        return rpt;
    }


}
