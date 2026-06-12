package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestListaTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseListaTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces.ITipoClientesListado;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.model.TipoClientesModel;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class TipoClientesListadoRepository implements ITipoClientesListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaTipoClientes ListaTipoClientes(RequestListaTipoClientes request) {
        ResponseListaTipoClientes rpt = new ResponseListaTipoClientes();
        List<TipoClientesModel> registros = new ArrayList<>();
        String SQL = "{ call CLIENTES.sp_ListarTipoCliente(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    TipoClientesModel item = new TipoClientesModel();
                item.setIdTipoCliente(rs.getLong("idTipoCliente"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getInt("estado"));
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setTipoClientes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_ListarTipoClientes", e);
        }
        return rpt;
    }


}
