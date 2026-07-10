package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestListaAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseListaAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces.IAlmacenListado;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenModel;
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
public class AlmacenListadoRepository implements IAlmacenListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaAlmacen ListaAlmacen(RequestListaAlmacen request) {
        ResponseListaAlmacen rpt = new ResponseListaAlmacen();
        List<AlmacenModel> registros = new ArrayList<>();
        String SQL = "{ call INVENTARIO.sp_ListarAlmacenes(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getEstado());
            Long empresaId = 1L;
            pstmt.setLong(2, empresaId);
            Long sucursalId = 1L;
            pstmt.setLong(3, sucursalId);

            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    AlmacenModel item = new AlmacenModel();
                item.setIdAlmacen(rs.getLong("idAlmacen"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getInt("estado"));
                item.setIdSucursal(rs.getLong("idSucursal"));
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
            rpt.setAlmacenes(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en ALMACEN.sp_ListarAlmacenes", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
