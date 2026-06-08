package com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestListaFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseListaFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.interfaces.IFamiliaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model.FamiliaModel;
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
public class FamiliaListadoRepository implements IFamiliaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaFamilia listaFamilia(RequestListaFamilia request) {
        ResponseListaFamilia rpt = new ResponseListaFamilia();
        List<FamiliaModel> familias = new ArrayList<>();

        String SQL = "{ call PRODUCTOS.sp_ListarFamilia (?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                FamiliaModel familia = new FamiliaModel();

                familia.setIdFamilia(rs.getLong("idFamilia"));
                familia.setDescripcion(rs.getString("descripcion"));
                familia.setImagenUrl(rs.getString("imagenUrl"));
                familia.setEstado(rs.getInt("estado"));
                familia.setFechaCreacion(
                        rs.getTimestamp("fechaCreacion") != null
                                ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                : null
                );

                familia.setFechaEdicion(
                        rs.getTimestamp("fechaEdicion") != null
                                ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                : null
                );

                familia.setFechaAnulacion(
                        rs.getTimestamp("fechaAnulacion") != null
                                ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                : null
                );
                familia.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                familia.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                familia.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                familias.add(familia);
            }
            rpt.setExito(true);
            rpt.setFamilia(familias);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
