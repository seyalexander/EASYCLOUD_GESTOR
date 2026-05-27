package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces.ISucursalesDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.model.SucursalesModel;
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
public class SucursalesDetalleRepository implements ISucursalesDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleSucursales DetalleSucursales(RequestDetalleSucursales request) {
        ResponseDetalleSucursales response = new ResponseDetalleSucursales();
        String SQL = "{ call CONFIGURACION.sp_ObtenerSucursalesPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdSucursales());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    SucursalesModel item = new SucursalesModel();
                    item.setIdSucursales(rs.getLong("idSucursales"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setEstado(rs.getInt("estado"));
                    response.setExito(true);
                    response.setMessage("Sucursales obtenido correctamente.");
                    response.setSucursales(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Sucursales.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_ObtenerSucursalesPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
