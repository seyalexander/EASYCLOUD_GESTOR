package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.request.RequestListaTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.application.dto.response.ResponseListaTipoPromociones;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.domain.interfaces.ITipoPromocionesListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class TipoPromocionesListadoRepository implements ITipoPromocionesListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaTipoPromociones listaTipoPromociones(RequestListaTipoPromociones request) {
        ResponseListaTipoPromociones rpt = new ResponseListaTipoPromociones();
        List<TipoPromocionesModel> registros = new ArrayList<>();
        String SQL = "{ call PRODUCTOS.sp_ListarTipoPromociones(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TipoPromocionesModel item = new TipoPromocionesModel();
                item.setIdTipoPromocion(rs.getLong("idTipoPromocion"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getInt("estado"));
                item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setTipoPromociones(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ListarTipoPromociones", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
