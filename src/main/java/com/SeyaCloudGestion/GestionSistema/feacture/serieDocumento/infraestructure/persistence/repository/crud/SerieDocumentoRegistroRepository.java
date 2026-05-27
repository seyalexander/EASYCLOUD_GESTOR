package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestRegistroSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseRegistroSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.interfaces.ISerieDocumentoRegistro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class SerieDocumentoRegistroRepository implements ISerieDocumentoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroSerieDocumento RegistroSerieDocumento(RequestRegistroSeries request) {
        ResponseRegistroSerieDocumento rpt = new ResponseRegistroSerieDocumento();
        String SQL = "{ call dbo.sp_RegistroSerieDocumento(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            //Long userId = 1L;
            //pstmt.setLong(1, userId);
            pstmt.setLong(1, request.getIdTipoDocumento());
            pstmt.setString(2, request.getSerie());
            pstmt.setLong(3, request.getCorrelativoActual());
            pstmt.setInt(4, request.getEsElectronico());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("SerieDocumento insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó SerieDocumento.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_RegistroSerieDocumento", e);
        }
        return rpt;
    }

}
