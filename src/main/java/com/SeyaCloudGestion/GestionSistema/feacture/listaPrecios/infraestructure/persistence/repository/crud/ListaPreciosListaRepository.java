package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestListarListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseListaListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseRegistroListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces.IListaPreciosListado;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.model.ListaPreciosModel;
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
public class ListaPreciosListaRepository implements IListaPreciosListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseListaListaPrecios ListarListaPrecios(RequestListarListaPrecios request) {
        ResponseListaListaPrecios rpt = new ResponseListaListaPrecios();
        List<ListaPreciosModel> listaPrecios = new ArrayList<>();

        String SQL = "{ call PRODUCTOS.sp_ListarListaPrecio (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ListaPreciosModel listaPrecio = new ListaPreciosModel();

                listaPrecio.setIdListaPrecio(rs.getLong("idListaPrecio"));
                listaPrecio.setDescripcion(rs.getString("descripcion"));
                listaPrecio.setEstado(rs.getInt("estado"));
                listaPrecio.setFechaCreacion(
                        rs.getTimestamp("fechaCreacion") != null
                                ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                : null
                );

                listaPrecio.setFechaEdicion(
                        rs.getTimestamp("fechaEdicion") != null
                                ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                : null
                );

                listaPrecio.setFechaAnulacion(
                        rs.getTimestamp("fechaAnulacion") != null
                                ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                : null
                );
                listaPrecio.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                listaPrecio.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                listaPrecio.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));
                listaPrecios.add(listaPrecio);
            }
            rpt.setExito(true);
            rpt.setListaPrecios(listaPrecios);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
