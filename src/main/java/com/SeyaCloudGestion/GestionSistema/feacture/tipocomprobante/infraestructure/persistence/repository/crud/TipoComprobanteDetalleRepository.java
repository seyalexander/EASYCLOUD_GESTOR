package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.repository.crud;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces.ITipoComprobanteDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.model.TipoComprobanteModel;
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
public class TipoComprobanteDetalleRepository implements ITipoComprobanteDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleTipoComprobante DetalleTipoComprobante(RequestDetalleTipoComprobante request) {
        ResponseDetalleTipoComprobante response = new ResponseDetalleTipoComprobante();
        TipoComprobanteModel tipoComprobante = null;

        String SQL = "{ call CONFIGURACION.sp_ObtenerTipoComprobantePorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdTipoComprobante());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    tipoComprobante = new TipoComprobanteModel();
                    tipoComprobante.setIdTipoComprobante(rs.getLong("idTipoComprobante"));
                    tipoComprobante.setDescripcion(rs.getString("descripcion"));
                    tipoComprobante.setCodigoSunat(rs.getString("codigoSunat"));
                    tipoComprobante.setEstado(rs.getInt("estado"));

                    tipoComprobante.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    tipoComprobante.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    tipoComprobante.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );

                    tipoComprobante.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    tipoComprobante.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    tipoComprobante.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("Tipo de comprobante obtenido correctamente");
                    response.setTipoCompobante(tipoComprobante);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró el tipo de comprobante");
                }
            }

        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage("Error al obtener el tipo de comprobante");
            log.error("Error en SISTEMA.sp_ObtenerTipoComprobantePorId", e);
        }

        return response;
    }
}