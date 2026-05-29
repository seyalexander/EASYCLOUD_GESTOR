package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestListaSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseListaSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.interfaces.ISucursalesListado;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.model.SucursalesModel;
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
public class SucursalesListadoRepository implements ISucursalesListado {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseListaSucursales ListaSucursales(RequestListaSucursales request) {
        ResponseListaSucursales rpt = new ResponseListaSucursales();
        List<SucursalesModel> registros = new ArrayList<>();
        String SQL = "{ call INVENTARIO.sp_ListarSucursales(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getEstado());
            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    SucursalesModel item = new SucursalesModel();
                item.setIdSucursales(rs.getLong("idSucursales"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getInt("estado"));
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
            rpt.setSucursales(registros);
            rpt.setMessage("Consulta realizada correctamente.");
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_ListarSucursales", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
