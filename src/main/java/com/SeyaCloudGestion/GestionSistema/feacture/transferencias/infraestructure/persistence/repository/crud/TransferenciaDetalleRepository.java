package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces.ITransferenciaDetalle;
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

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class TransferenciaDetalleRepository implements ITransferenciaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleTransferencia DetalleTransferencia(RequestDetalleTransferencia request) {
        ResponseDetalleTransferencia response = new ResponseDetalleTransferencia();
        String SQL = "{ call ALMACEN.sp_ObtenerTransferenciaPorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    TransferenciaModel item = new TransferenciaModel();
                    item.setIdTransferencia(rs.getLong("idTransferencia"));
                    item.setIdAlmacenOrigen(rs.getLong("idAlmacenOrigen"));
                    item.setIdAlmacenDestino(rs.getLong("idAlmacenDestino"));
                    item.setFecha(rs.getString("fecha"));
                    item.setEstado(rs.getString("estado"));
                    response.setExito(true);
                    response.setMessage("Transferencia obtenido correctamente.");
                    response.setTransferencia(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Transferencia.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ObtenerTransferenciaPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
