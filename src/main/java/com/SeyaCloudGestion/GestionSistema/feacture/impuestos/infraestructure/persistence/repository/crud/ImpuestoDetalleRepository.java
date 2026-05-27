package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestDetalleImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseDetalleImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces.IImpuestoDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.model.ImpuestoModel;
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
public class ImpuestoDetalleRepository implements IImpuestoDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleImpuesto DetalleImpuesto(RequestDetalleImpuesto request) {
        ResponseDetalleImpuesto response = new ResponseDetalleImpuesto();
        String SQL = "{ call dbo.sp_ObtenerImpuestoPorId() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetro id definido en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ImpuestoModel item = new ImpuestoModel();
                    item.setIdImpuesto(rs.getLong("idImpuesto"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setPorcentaje(rs.getDouble("porcentaje"));
                    item.setEsPrincipal(rs.getInt("esPrincipal"));
                    item.setEstado(rs.getInt("estado"));
                    response.setExito(true);
                    response.setMessage("Impuesto obtenido correctamente.");
                    response.setImpuesto(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Impuesto.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en dbo.sp_ObtenerImpuestoPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
