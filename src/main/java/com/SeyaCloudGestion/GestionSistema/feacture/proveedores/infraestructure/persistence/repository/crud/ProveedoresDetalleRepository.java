package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestDetalleProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseDetalleProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model.ProveedorModel;
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
    public ResponseDetalleProveedor DetalleProveedores(RequestDetalleProveedor request) {
        ResponseDetalleProveedor response = new ResponseDetalleProveedor();
        String SQL = "{ call COMPRAS.sp_ObtenerProveedorPorId(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdProveedor());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProveedorModel item = new ProveedorModel();
                    item.setIdProveedor(rs.getLong("idProveedor"));
                    item.setRazonSocial(rs.getString("razonSocial"));
                    item.setRuc(rs.getString("ruc"));
                    item.setIdTipoDocumento(rs.getLong("idTipoDocumentoIdentidad"));
                    item.setRuc(rs.getString("telefono"));
                    item.setEmail(rs.getString("email"));
                    item.setDireccion(rs.getString("direccion"));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("Proveedores obtenidos correctamente.");
                    response.setProveedor(item);
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
