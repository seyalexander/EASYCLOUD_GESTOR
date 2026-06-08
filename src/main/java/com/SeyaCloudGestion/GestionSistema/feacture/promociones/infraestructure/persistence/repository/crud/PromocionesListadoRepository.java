package com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request.RequestListaPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.response.ResponseListaPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.promociones.domain.interfaces.IPromocionesListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class PromocionesListadoRepository implements IPromocionesListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaPromociones listaPromociones(RequestListaPromociones request) {
        ResponseListaPromociones rpt = new ResponseListaPromociones();
        List<PromocionesModel> registros = new ArrayList<>();
        String SQL = "{ call PRODUCTOS.sp_ListarPromociones(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PromocionesModel item = new PromocionesModel();
                item.setIdPromocion(rs.getLong("idPromocion"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setIdTipoPromocion(rs.getLong("idTipoPromocion"));
                item.setFechaInicio((rs.getTimestamp("fechaInicio") != null ? rs.getTimestamp("fechaInicio").toLocalDateTime() : null));
                item.setFechaFin((rs.getTimestamp("fechaFin") != null ? rs.getTimestamp("fechaFin").toLocalDateTime() : null));
                item.setEstado(rs.getInt("estado"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setPromociones(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ListarPromociones", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
