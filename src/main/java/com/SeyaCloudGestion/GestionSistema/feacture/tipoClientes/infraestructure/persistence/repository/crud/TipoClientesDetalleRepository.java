package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces.ITipoClientesDetalle;
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

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class TipoClientesDetalleRepository implements ITipoClientesDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleTipoClientes DetalleTipoClientes(RequestDetalleTipoClientes request) {
        ResponseDetalleTipoClientes response = new ResponseDetalleTipoClientes();
        String SQL = "{ call CLIENTES.sp_ObtenerTipoClientePorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTipoClientes());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TipoClientesModel item = new TipoClientesModel();
                    item.setIdTipoCliente(rs.getLong("idTipoCliente"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setEstado(rs.getInt("estado"));

                    response.setExito(true);
                    response.setMessage("TipoClientes obtenido correctamente.");
                    response.setTipoClientes(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró TipoClientes.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_ObtenerTipoClientesPorId", e);
        }
        return response;
    }

}
