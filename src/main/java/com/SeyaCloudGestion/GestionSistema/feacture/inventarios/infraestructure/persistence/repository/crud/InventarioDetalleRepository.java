package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces.IInventarioDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.InventarioModel;
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
public class InventarioDetalleRepository implements IInventarioDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseDetalleInventario DetalleInventario(RequestDetalleInventario request) {
        ResponseDetalleInventario response = new ResponseDetalleInventario();
        String SQL = "{ call ALMACEN.sp_ObtenerInventarioPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdInventario());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    InventarioModel item = new InventarioModel();

                    response.setExito(true);
                    response.setMessage("Inventario obtenido correctamente.");
                    response.setInventario(item);
                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró Inventario.");
                }
            }
        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ObtenerInventarioPorId", e);
        }
        return response;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
