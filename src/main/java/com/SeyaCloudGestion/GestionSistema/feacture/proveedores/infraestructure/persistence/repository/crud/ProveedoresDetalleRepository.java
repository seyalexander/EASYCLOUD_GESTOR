package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestDetalleProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseDetalleProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresDetalle;
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

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ProveedoresDetalleRepository implements IProveedoresDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleProveedores DetalleProveedores(RequestDetalleProveedores request) {
        ResponseDetalleProveedores response = new ResponseDetalleProveedores();
        String SQL = "{ call COMPRAS.sp_ObtenerProveedoresPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdProveedores());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProveedoresModel item = new ProveedoresModel();
                    item.setIdProveedor(rs.getLong("idProveedor"));
                    item.setRazonSocial(rs.getString("razonSocial"));
                    item.setRuc(rs.getString("ruc"));
                    item.setEmail(rs.getString("email"));
                    item.setDireccion(rs.getString("direccion"));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("Proveedores obtenido correctamente.");
                    response.setProveedores(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Proveedores.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en COMPRAS.sp_ObtenerProveedoresPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
