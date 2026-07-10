package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.interfaces.ITipoDocumentoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoCaracter;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoDocumentoModel;
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
public class TipoDocumentoDetalleRepository implements ITipoDocumentoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleTipoDocumento DetalleTipoDocumento(RequestDetalleTipoDocumento request) {
        ResponseDetalleTipoDocumento response = new ResponseDetalleTipoDocumento();
        TipoDocumentoModel tipoDocumento = null;

        String SQL = "{ call CONFIGURACION.sp_ObtenerTipoDocumentoIdentidadPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTipoDocumentos());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    tipoDocumento = new TipoDocumentoModel();
                    tipoDocumento.setIdTipoDocumentos(rs.getLong("idTipoDocumentoIdentidad"));
                    tipoDocumento.setDescripcion(rs.getString("descripcion"));
                    tipoDocumento.setEstado(rs.getInt("estado"));
                    tipoDocumento.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    tipoDocumento.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    tipoDocumento.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    tipoDocumento.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    tipoDocumento.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    tipoDocumento.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));
                    tipoDocumento.setLongitudMin(rs.getInt("longitudMin"));
                    tipoDocumento.setLongitudMax(rs.getInt("longitudMax"));
                    tipoDocumento.setCodigoSunat(rs.getString("codigoSunat"));
                    tipoDocumento.setTipoCaracter(
                            TipoCaracter.fromCodigo(rs.getInt("tipoCaracter"))
                    );
                    response.setExito(true);
                    response.setMessage("Tipo Documento obtenido correctamente");
                    response.setTipoDocumento(tipoDocumento);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró al Tipo Documento");
                }
            }

        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage("Error al obtener la Tipo Documento");
            log.error("Error en CONFIGURACION.sp_ObtenerTipoDocumentoPorId", e);
        }

        return response;
    }
}
