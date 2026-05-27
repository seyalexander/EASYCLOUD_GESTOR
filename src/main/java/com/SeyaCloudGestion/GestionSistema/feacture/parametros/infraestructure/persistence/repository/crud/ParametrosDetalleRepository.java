package com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestDetalleParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseDetalleParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces.IParametrosDetalle;
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

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ParametrosDetalleRepository implements IParametrosDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleParametros DetalleParametros(RequestDetalleParametros request) {
        ResponseDetalleParametros response = new ResponseDetalleParametros();
        String SQL = "{ call CONFIGURACION.sp_ObtenerParametrosPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdParametros());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ParametrosModel item = new ParametrosModel();
                    item.setIdParametroSistema(rs.getLong("idParametroSistema"));
                    item.setClave(rs.getString("clave"));
                    item.setValor(rs.getString("valor"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setEstado(rs.getInt("estado"));
                    response.setExito(true);
                    response.setMessage("Parametros obtenido correctamente.");
                    response.setParametros(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Parametros.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en CONFIGURACION.sp_ObtenerParametrosPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
