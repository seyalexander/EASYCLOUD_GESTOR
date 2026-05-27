package com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestDetallePromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseDetallePromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.domain.interfaces.IPromocionesDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.model.PromocionesModel;
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
public class PromocionesDetalleRepository implements IPromocionesDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetallePromociones DetallePromociones(RequestDetallePromociones request) {
        ResponseDetallePromociones response = new ResponseDetallePromociones();
        String SQL = "{ call PRODUCTOS.sp_ObtenerPromocionesPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdPromociones());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PromocionesModel item = new PromocionesModel();
                    item.setIdPromocion(rs.getLong("idPromocion"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setIdTipoPromocion(rs.getLong("idTipoPromocion"));
                    item.setFechaInicio((rs.getTimestamp("fechaInicio") != null ? rs.getTimestamp("fechaInicio").toLocalDateTime() : null));
                    item.setFechaFin((rs.getTimestamp("fechaFin") != null ? rs.getTimestamp("fechaFin").toLocalDateTime() : null));
                    item.setEstado(rs.getInt("estado"));
                    response.setExito(true);
                    response.setMessage("Promociones obtenido correctamente.");
                    response.setPromociones(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Promociones.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ObtenerPromocionesPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
