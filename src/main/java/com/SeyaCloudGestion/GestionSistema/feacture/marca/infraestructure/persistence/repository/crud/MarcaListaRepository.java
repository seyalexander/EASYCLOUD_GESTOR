package com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.model.MarcaModel;
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
public class MarcaListaRepository implements IMarcaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseListaMarca ListaMarca(RequestListaMarca request) {
        ResponseListaMarca rpt = new ResponseListaMarca();
        List<MarcaModel> marcas = new ArrayList<>();

        String SQL = "{ call PRODUCTOS.sp_ListarMarca (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MarcaModel marca = new MarcaModel();

                marca.setIdMarca(rs.getLong("idMarca"));
                marca.setDescripcion(rs.getString("descripcion"));
                marca.setEstado(rs.getInt("estado"));
                marca.setFechaCreacion(
                        rs.getTimestamp("fechaCreacion") != null
                                ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                : null
                );

                marca.setFechaEdicion(
                        rs.getTimestamp("fechaEdicion") != null
                                ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                : null
                );

                marca.setFechaAnulacion(
                        rs.getTimestamp("fechaAnulacion") != null
                                ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                : null
                );
                marca.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                marca.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                marca.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));
                marcas.add(marca);
            }
            rpt.setExito(true);
            rpt.setMarcas(marcas);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
