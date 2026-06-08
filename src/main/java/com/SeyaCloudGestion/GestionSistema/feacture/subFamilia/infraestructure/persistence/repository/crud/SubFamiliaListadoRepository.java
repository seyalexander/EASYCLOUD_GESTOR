package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestListaSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseListaSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.interfaces.ISubFamiliaLista;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.model.subFamiliaModel;
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
public class SubFamiliaListadoRepository implements ISubFamiliaLista {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseListaSubFamilia ListaSubFamilia(RequestListaSubFamilia request) {
        ResponseListaSubFamilia rpt = new ResponseListaSubFamilia();
        List<subFamiliaModel> subFamilias = new ArrayList<>();

        String SQL = "{ call PRODUCTOS.sp_ListarSubFamilia (?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId = 1L;

            pstmt.setInt(1, request.getEstado());
            pstmt.setLong(2, request.getIdFamilia());
            pstmt.setLong(3, empresaId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                subFamiliaModel familia = new subFamiliaModel();

                familia.setIdFamilia(rs.getLong("idFamilia"));
                familia.setFamiliaDescripcion(rs.getString("familiaDescripcion"));
                familia.setIdSubFamilia(rs.getLong("idSubFamilia"));
                familia.setSubFamiliaDescripcion(rs.getString("subFamiliaDescripcion"));
                familia.setImagenUrl(rs.getString("imagenUrl"));
                familia.setEstado(rs.getInt("estado"));
                familia.setFechaCreacion(rs.getString("fechaCreacion"));
                familia.setFechaEdicion(rs.getString("fechaEdicion"));
                familia.setFechaAnulacion(rs.getString("fechaAnulacion"));
                familia.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                familia.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                familia.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                subFamilias.add(familia);
            }
            rpt.setExito(true);
            rpt.setSubfamilias(subFamilias);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
