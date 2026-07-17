package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestListaDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseListaTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.domain.interfaces.ITransferenciaDetalleListado;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.persistence.model.DetalleTransferenciaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model.TransferenciaModel;
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
public class ListadoDetalleTransferenciaRepository implements ITransferenciaDetalleListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaTransferenciaDetalle listaDetalleTransferencia(RequestListaDetalleTransferencia request) {
        ResponseListaTransferenciaDetalle rpt = new ResponseListaTransferenciaDetalle();
        List<DetalleTransferenciaModel> registros = new ArrayList<>();
        String SQL = "{ call INVENTARIO.sp_ListarTransferenciaDetallePorIdTransferencia(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTransferencia());
            Long empresaId = 1L;
            Long sucursalId = 1L;
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DetalleTransferenciaModel item = new DetalleTransferenciaModel();
                item.setIdTransferenciaDetalle(rs.getLong("idTransferenciaDetalle"));
                item.setIdTransferencia(rs.getLong("idTransferencia"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setCantidad(rs.getDouble("cantidad"));
                item.setCostoUnitario(rs.getDouble("costoUnitario"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setDetalles(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarTransferencia", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
