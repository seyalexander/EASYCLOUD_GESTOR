package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestListaProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseListaProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresListado;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model.ProveedoresModel;
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
public class ProveedoresListadoRepository implements IProveedoresListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaProveedores listaProveedores(RequestListaProveedores request) {
        ResponseListaProveedores rpt = new ResponseListaProveedores();
        List<ProveedoresModel> registros = new ArrayList<>();
        String SQL = "{ call COMPRAS.sp_ListarProveedores(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProveedoresModel item = new ProveedoresModel();
                item.setIdProveedor(rs.getLong("idProveedor"));
                item.setRazonSocial(rs.getString("razonSocial"));
                item.setRuc(rs.getString("ruc"));
                item.setEmail(rs.getString("email"));
                item.setDireccion(rs.getString("direccion"));
                item.setEstado(rs.getInt("estado"));
                item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setProveedores(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ListarProveedores", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
