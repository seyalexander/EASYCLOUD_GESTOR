package com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseListaEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.model.EmpresaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestListaMonedas;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseListaMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces.IMonedaListado;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.model.MonedaModel;
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
public class MonedaListadoRepository implements IMonedaListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseListaMoneda ListaMoneda(RequestListaMonedas request) {
        ResponseListaMoneda rpt = new ResponseListaMoneda();
        List<MonedaModel> monedas = new ArrayList<>();

        String SQL = "{ call CONFIGURACION.sp_ListarMoneda  (?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setInt(1, request.getEstado());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MonedaModel moneda = new MonedaModel();

                moneda.setIdMoneda(rs.getLong("idMoneda"));
                moneda.setDescripcion(rs.getString("descripcion"));
                moneda.setSimbolo(rs.getString("simbolo"));
                moneda.setEsPrincipal(rs.getInt("esPrincipal"));
                moneda.setEstado(rs.getInt("estado"));
                moneda.setFechaCreacion(
                        rs.getTimestamp("fechaCreacion") != null
                                ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                : null
                );

                moneda.setFechaEdicion(
                        rs.getTimestamp("fechaEdicion") != null
                                ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                : null
                );

                moneda.setFechaAnulacion(
                        rs.getTimestamp("fechaAnulacion") != null
                                ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                : null
                );
                moneda.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                moneda.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                moneda.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                monedas.add(moneda);
            }
            rpt.setExito(true);
            rpt.setMonedas(monedas);
            rpt.setMessage("Consulta realizada correctamente.");

        } catch (Exception e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
        }

        return rpt;
    }
}
