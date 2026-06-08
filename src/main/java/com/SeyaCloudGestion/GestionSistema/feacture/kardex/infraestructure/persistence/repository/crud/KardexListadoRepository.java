package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.domain.interfaces.IKardexListado;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.KardexModel;
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
public class KardexListadoRepository implements IKardexListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaKardex listaKardex(RequestListaKardex request) {
        ResponseListaKardex rpt = new ResponseListaKardex();
        List<KardexModel> registros = new ArrayList<>();
        String SQL = "{ call ALMACEN.sp_ListarKardex(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KardexModel item = new KardexModel();

                    registros.add(item);
                }
            }

            rpt.setExito(true);
            rpt.setKardexs(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarKardex", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
