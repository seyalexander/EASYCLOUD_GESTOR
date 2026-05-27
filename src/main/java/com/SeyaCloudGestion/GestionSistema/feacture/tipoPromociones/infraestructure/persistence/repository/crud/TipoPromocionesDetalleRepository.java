package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.request.RequestDetalleTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response.ResponseDetalleTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.domain.interfaces.ITipoPromocionesDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.model.TipoPromocionesModel;
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
public class TipoPromocionesDetalleRepository implements ITipoPromocionesDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleTipoPromociones DetalleTipoPromociones(RequestDetalleTipoPromociones request) {
        ResponseDetalleTipoPromociones response = new ResponseDetalleTipoPromociones();
        String SQL = "{ call PRODUCTOS.sp_ObtenerTipoPromocionesPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdTipoPromociones());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TipoPromocionesModel item = new TipoPromocionesModel();
                    item.setIdTipoPromocion(rs.getLong("idTipoPromocion"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setEstado(rs.getInt("estado"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("TipoPromociones obtenido correctamente.");
                    response.setTipoPromociones(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró TipoPromociones.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ObtenerTipoPromocionesPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
