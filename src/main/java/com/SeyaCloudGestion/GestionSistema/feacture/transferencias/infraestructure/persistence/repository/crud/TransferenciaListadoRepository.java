package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces.ITransferenciaListado;
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
public class TransferenciaListadoRepository implements ITransferenciaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaTransferencia listaTransferencia(RequestListaTransferencia request) {
        ResponseListaTransferencia rpt = new ResponseListaTransferencia();
        List<TransferenciaModel> registros = new ArrayList<>();
        String SQL = "{ call ALMACEN.sp_ListarTransferencia() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TransferenciaModel item = new TransferenciaModel();
                item.setIdTransferencia(rs.getLong("idTransferencia"));
                item.setIdAlmacenOrigen(rs.getLong("idAlmacenOrigen"));
                item.setIdAlmacenDestino(rs.getLong("idAlmacenDestino"));
                item.setFecha(rs.getString("fecha"));
                item.setEstado(rs.getString("estado"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setTransferencias(registros);
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
