package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestListaCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.ResponseListaCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.interfaces.ICodigoBarraListado;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class CodigoBarraListadoRepository implements ICodigoBarraListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaCodigoBarra ListaCodigoBarra( ) {
        ResponseListaCodigoBarra rpt = new ResponseListaCodigoBarra();
        List<CodigoBarraModel> registros = new ArrayList<>();
        String SQL = "{ call PRODUCTOS.sp_ListarCodigoBarra (?)}";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long empresaId = 1L;
            pstmt.setLong(1, empresaId);

            ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    CodigoBarraModel item = new CodigoBarraModel();
                item.setIdCodigoBarra(rs.getLong("idCodigoBarra"));
                item.setIdArticulo(rs.getLong("idArticulo"));
                item.setCodigo(rs.getString("codigo"));
                item.setPrincipal(rs.getInt("principal"));
                item.setFechaIngreso((rs.getTimestamp("fechaIngreso") != null ? rs.getTimestamp("fechaIngreso").toLocalDateTime() : null));
                    item.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    item.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    item.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    item.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    item.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));
                    registros.add(item);
                }

            rpt.setExito(true);
            rpt.setCodigoBarras(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_ListarCodigoBarra", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
