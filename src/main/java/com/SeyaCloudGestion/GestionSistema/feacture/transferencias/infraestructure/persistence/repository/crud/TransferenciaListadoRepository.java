package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces.ITransferenciaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model.EstadoTransferencia;
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
        String SQL = "{ call INVENTARIO.sp_ListarTransferencias(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long sucursalId = 1L;
            Long empresaId = 1L;
            setParameter(pstmt, 1, request.getEstado().name());
            pstmt.setLong(2, empresaId);
            pstmt.setLong(3, sucursalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TransferenciaModel item = new TransferenciaModel();
                    item.setIdTransferencia(rs.getLong("idTransferencia"));
                    item.setIdAlmacenOrigen(rs.getLong("idAlmacenOrigen"));
                    item.setIdAlmacenDestino(rs.getLong("idAlmacenDestino"));
                    item.setFecha(
                            rs.getTimestamp("fecha") != null
                                    ? rs.getTimestamp("fecha").toLocalDateTime()
                                    : null
                    );
                    item.setEstado(EstadoTransferencia.valueOf(rs.getString("estado")));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));

                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setTransferencias(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ListarTransferencias", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
