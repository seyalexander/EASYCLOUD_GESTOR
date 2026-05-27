package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.RequestListaDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.ResponseListaDireccionesClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.domain.interfaces.IDireccionesClientesListado;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.model.DireccionesClientesModel;
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
public class DireccionesClientesListadoRepository implements IDireccionesClientesListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaDireccionesClientes listaDireccionesClientes(RequestListaDireccionesClientes request) {
        ResponseListaDireccionesClientes rpt = new ResponseListaDireccionesClientes();
        List<DireccionesClientesModel> registros = new ArrayList<>();
        String SQL = "{ call VENTAS.sp_ListarDireccionesClientes(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DireccionesClientesModel item = new DireccionesClientesModel();
                item.setIdDireccionCliente(rs.getLong("idDireccionCliente"));
                item.setIdCliente(rs.getLong("idCliente"));
                item.setDireccion(rs.getString("direccion"));
                item.setDepartamento(rs.getString("departamento"));
                item.setProvincia(rs.getString("provincia"));
                item.setDistrito(rs.getString("distrito"));
                item.setReferencia(rs.getString("referencia"));
                item.setEstado(rs.getInt("estado"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setDireccionesClientes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en VENTAS.sp_ListarDireccionesClientes", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
