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

        String SQL = "{ call PRODUCTOS.sp_ListarUnidadMedida (?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UnidadMedidaModel item = new UnidadMedidaModel();

                item.setIdUnidadMedida(rs.getLong("idUnidadMedida"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setSiglas(rs.getString("siglas"));
                item.setEstado(rs.getInt("estado"));
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

                item.setFechaAnulacion(
                        rs.getTimestamp("fechaAnulacion") != null
                                ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                : null
                );
                item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                item.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));
                unidadesMedidas.add(item);
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
