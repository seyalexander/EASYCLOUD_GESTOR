package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseListaTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoDocumentoModel;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces.IUnidadMedidaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.model.UnidadMedidaModel;
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
public class UnidadMedidaListaRepository implements IUnidadMedidaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseListaUnidadMedida listaUnidadMedida(RequestListaUnidadMedida request) {
        ResponseListaUnidadMedida rpt = new ResponseListaUnidadMedida();
        List<UnidadMedidaModel> unidadesMedidas = new ArrayList<>();

        String SQL = "{ call PRODUCTOS.sp_ListarUnidadMedida (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UnidadMedidaModel tipoDocumento = new UnidadMedidaModel();

                tipoDocumento.setIdUnidadMedida(rs.getLong("idUnidadMedida"));
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
                unidadesMedidas.add(tipoDocumento);
            }
            rpt.setExito(true);
            rpt.setUnidadesMedida(unidadesMedidas);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
