package com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestListaParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseListaParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces.IParametrosListado;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.model.ParametrosModel;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ParametrosListadoRepository implements IParametrosListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaParametros listaParametros(RequestListaParametros request) {
        ResponseListaParametros rpt = new ResponseListaParametros();
        List<ParametrosModel> registros = new ArrayList<>();
        String SQL = "{ call CONFIGURACION.sp_ListarParametros(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ParametrosModel item = new ParametrosModel();
                item.setIdParametroSistema(rs.getLong("idParametroSistema"));
                item.setClave(rs.getString("clave"));
                item.setValor(rs.getString("valor"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getInt("estado"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setParametros(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_ListarParametros", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
