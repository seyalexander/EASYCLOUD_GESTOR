package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.repository.crud;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestListaTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseListaTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces.ITipoComprobanteListado;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.model.TipoComprobanteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional("sqlServerTransactionManager")
public class TipoComprobanteListadoRepository implements ITipoComprobanteListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaTipoComprobante listaTipoComprobante(RequestListaTipoComprobante request) {
        ResponseListaTipoComprobante rpt = new ResponseListaTipoComprobante();
        List<TipoComprobanteModel> tipoComprobantes = new ArrayList<>();

        String SQL = "{ call CONFIGURACION.sp_ListarTipoComprobante (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                TipoComprobanteModel tipoComprobante = new TipoComprobanteModel();

                tipoComprobante.setIdTipoComprobante(rs.getLong("idTipoComprobante"));
                tipoComprobante.setDescripcion(rs.getString("descripcion"));
                tipoComprobante.setCodigoSunat(rs.getString("codigoSunat")); // Adaptado de 'imagenUrl' a un campo de comprobante
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

                tipoComprobantes.add(tipoComprobante);
            }

            rpt.setExito(true);
            rpt.setTipoComprobante(tipoComprobantes);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}