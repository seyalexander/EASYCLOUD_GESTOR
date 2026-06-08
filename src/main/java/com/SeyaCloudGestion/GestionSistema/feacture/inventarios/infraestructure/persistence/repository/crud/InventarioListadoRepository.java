package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestListaInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseListaInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces.IInventarioListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class InventarioListadoRepository implements IInventarioListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaInventario listaInventario(RequestListaInventario request) {
        ResponseListaInventario rpt = new ResponseListaInventario();
        List<InventarioModel> registros = new ArrayList<>();
        String SQL = "{ call ALMACEN.sp_ListarInventario(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    InventarioModel item = new InventarioModel();

                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setInventarios(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarInventario", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
