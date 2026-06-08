package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request.RequestDetalleAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response.ResponseDetalleAjuste;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.domain.interfaces.IAjustesDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.model.AjustesModel;
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
public class AjusteDetalleRepository implements IAjustesDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleAjuste DetalleAjustes(RequestDetalleAjuste request) {
        ResponseDetalleAjuste response = new ResponseDetalleAjuste();
        String SQL = "{ call ALMACEN.sp_ObtenerAjustesPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdAjuste());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AjustesModel item = new AjustesModel();

                    response.setExito(true);
                    response.setMessage("Ajustes obtenido correctamente.");
                    response.setAjustes(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Ajustes.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ObtenerAjustesPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
