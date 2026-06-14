package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseRegistroFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestRegistroTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseRegistroTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces.ITipoDocumentoRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
@Transactional("sqlServerTransactionManager")
public class TipoDocumentoRegistroRepository implements ITipoDocumentoRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTipoDocumento RegistroTipoDocumento(RequestRegistroTipoDocumento request, long userAutenticado) {
        ResponseRegistroTipoDocumento rpt = new ResponseRegistroTipoDocumento();

        String SQL = "{ call CONFIGURACION.sp_RegistroTipoDocumentoIdentidad(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setString(1, request.getDescripcion());
            pstmt.setInt(2, request.getLongitudMin());
            pstmt.setInt(3, request.getLongitudMax());
            pstmt.setInt(4, request.getTipoCaracter().getCodigo());
            pstmt.setString(5, request.getCodigoSunat());
            pstmt.setLong(6, userAutenticado);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Tipo Documento insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó un Tipo Documento.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un tipo documento con esa descripcion.");
            } else {
                rpt.setMessage("Error al registrar el tipo documento.");
            }
        }
        return rpt;
    }
}
