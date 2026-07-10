package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestDetalleCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseDetalleCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces.ICodigoBarraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.model.CodigoBarraModel;
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
public class CodigoBarraDetalleRepository implements ICodigoBarraDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleCodigoBarra DetalleCodigoBarra(RequestDetalleCodigoBarra request) {
        ResponseDetalleCodigoBarra response = new ResponseDetalleCodigoBarra();
        String SQL = "{ call PRODUCTOS.sp_ObtenerCodigoBarraPorId(?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1,request.getIdCodigoBarra());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CodigoBarraModel item = new CodigoBarraModel();
                    item.setIdCodigoBarra(rs.getLong("idCodigoBarra"));
                    item.setIdArticulo(rs.getLong("idArticulo"));
                    item.setCodigo(rs.getString("codigo"));
                    item.setPrincipal(rs.getInt("principal"));
                    item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    response.setExito(true);
                    response.setMessage("CodigoBarra obtenido correctamente.");
                    response.setCodigoBarra(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró CodigoBarra.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ObtenerCodigoBarraPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
