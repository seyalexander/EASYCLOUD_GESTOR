package com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestDetalleEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseDetalleEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.interfaces.IEmpresaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.model.EmpresaModel;
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
public class EmpresaDetalleRepository implements IEmpresaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleEmpresa DetalleEmpresa(RequestDetalleEmpresa request) {
        ResponseDetalleEmpresa response = new ResponseDetalleEmpresa();
        EmpresaModel empresa = null;

        String SQL = "{ call CONFIGURACION.sp_ObtenerEmpresaPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdEmpresa());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    empresa = new EmpresaModel();
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

                    response.setExito(true);
                    response.setMessage("Tipo Documento obtenido correctamente");
                    response.setEmpresa(empresa);

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
