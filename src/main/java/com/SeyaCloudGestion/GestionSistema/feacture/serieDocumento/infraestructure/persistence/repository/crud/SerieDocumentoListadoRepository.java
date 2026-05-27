package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestListaSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseListaSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces.ISerieDocumentoListado;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.model.SerieDocumentoModel;
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
public class SerieDocumentoListadoRepository implements ISerieDocumentoListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaSerieDocumento listaSerieDocumento(RequestListaSerieDocumento request) {
        ResponseListaSerieDocumento rpt = new ResponseListaSerieDocumento();
        List<SerieDocumentoModel> registros = new ArrayList<>();
        String SQL = "{ call dbo.sp_ListarSerieDocumento(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SerieDocumentoModel item = new SerieDocumentoModel();
                item.setIdSerieDocumento(rs.getLong("idSerieDocumento"));
                item.setIdTipoDocumento(rs.getLong("idTipoDocumento"));
                item.setIdEmpresa(rs.getLong("idEmpresa"));
                item.setSerie(rs.getString("serie"));
                item.setCorrelativoActual(rs.getLong("correlativoActual"));
                item.setEsElectronico(rs.getInt("esElectronico"));
                item.setEstado(rs.getInt("estado"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setSerieDocumentos(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_ListarSerieDocumento", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
