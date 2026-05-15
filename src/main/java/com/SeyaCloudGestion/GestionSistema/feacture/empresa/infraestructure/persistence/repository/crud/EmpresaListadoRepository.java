package com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestListaEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseListaEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces.IEmpresaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.model.EmpresaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseListaTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoDocumentoModel;
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
public class EmpresaListadoRepository implements IEmpresaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseListaEmpresa listaEmpresa(RequestListaEmpresa request) {
        ResponseListaEmpresa rpt = new ResponseListaEmpresa();
        List<EmpresaModel> empresas = new ArrayList<>();

        String SQL = "{ call CONFIGURACION.sp_ListarEmpresa (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                EmpresaModel empresa = new EmpresaModel();

                empresa.setIdEmpresa(rs.getLong("idEmpresa"));
                empresa.setImagenUrl(rs.getString("imagenUrl"));
                empresa.setRazonSocial(rs.getString("razonSocial"));
                empresa.setRuc(rs.getString("ruc"));
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setEmail(rs.getString("email"));
                empresa.setLogoUrl(rs.getString("logoUrl"));
                empresa.setEstado(rs.getInt("estado"));
                empresa.setFechaCreacion(
                        rs.getTimestamp("fechaCreacion") != null
                                ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                : null
                );

                empresa.setFechaEdicion(
                        rs.getTimestamp("fechaEdicion") != null
                                ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                : null
                );

                empresa.setFechaAnulacion(
                        rs.getTimestamp("fechaAnulacion") != null
                                ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                : null
                );
                empresa.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                empresa.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                empresa.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                empresas.add(empresa);
            }
            rpt.setExito(true);
            rpt.setEmpresas(empresas);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
