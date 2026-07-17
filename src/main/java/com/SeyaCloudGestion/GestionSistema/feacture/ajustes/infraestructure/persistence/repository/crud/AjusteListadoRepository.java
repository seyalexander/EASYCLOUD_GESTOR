package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestListaAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseListaAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces.IAjustesListado;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.model.AjustesModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class AjusteListadoRepository implements IAjustesListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaAjuste listaAjustes(RequestListaAjuste request) {
        ResponseListaAjuste rpt = new ResponseListaAjuste();
        List<AjustesModel> registros = new ArrayList<>();
        String SQL = "{ call INVENTARIO.sp_ListarAjusteStock(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            Long empresaId = 1L;
            Long sucursalId = 1L;
            setParameter(pstmt, 1, request.getIdArticulo());
            setParameter(pstmt, 2, request.getIdAlmacen());
            pstmt.setLong(3, empresaId);
            pstmt.setLong(  4, sucursalId);

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    AjustesModel item = new AjustesModel();
                    item.setIdAjusteStock(rs.getLong("idAjusteStock"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setIdSucursal(rs.getLong("idSucursal"));
                    item.setIdAlmacen(rs.getLong("idAlmacen"));
                    item.setCantidad(rs.getDouble("cantidad"));
                    item.setMotivo(rs.getString("motivo"));

                    Timestamp fechaAjuste = rs.getTimestamp("fechaAjuste");
                    if (fechaAjuste != null) {
                        item.setFechaAjuste(fechaAjuste.toLocalDateTime());
                    }

                    item.setIdUsuario(rs.getLong("idUsuario"));

                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setAjustes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ListarAjusteStocks", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
