package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.RequestListaImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.ResponseListaImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.domain.interfaces.IImpuestoListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ImpuestoListadoRepository implements IImpuestoListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaImpuesto listaImpuesto(RequestListaImpuesto request) {
        ResponseListaImpuesto rpt = new ResponseListaImpuesto();
        List<ImpuestoModel> registros = new ArrayList<>();
        String SQL = "{ call dbo.sp_ListarImpuesto() }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            // Sin parámetros de filtro definidos en el request.

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ImpuestoModel item = new ImpuestoModel();
                item.setIdImpuesto(rs.getLong("idImpuesto"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setPorcentaje(rs.getDouble("porcentaje"));
                item.setEsPrincipal(rs.getInt("esPrincipal"));
                item.setEstado(rs.getInt("estado"));
                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setImpuestos(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en dbo.sp_ListarImpuesto", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
