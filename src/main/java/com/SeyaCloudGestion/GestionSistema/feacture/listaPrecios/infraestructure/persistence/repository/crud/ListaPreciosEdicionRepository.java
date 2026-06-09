package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarEstadoListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseEditarEstadoListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces.IListaPreciosEdicion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class ListaPreciosEdicionRepository implements IListaPreciosEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllListaPrecios EditarAllListaPrecios(RequestEditarAllListaPrecios request) {
        ResponseEditarAllListaPrecios rpt = new ResponseEditarAllListaPrecios();
        String SQL = "{ call PRODUCTOS.sp_EditarListaPrecio(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            pstmt.setLong(1, request.getIdListaPrecios());
            pstmt.setString(2,request.getDescripcion());
            pstmt.setInt(3,request.getEstado());
            Long userId = 1L;
            pstmt.setLong(4, userId);
            Long empresaId = 1L;
            pstmt.setLong(5, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ListaPrecios actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó ListaPrecios.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarListaPrecios", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoListaPrecios EditarEstadoListaPrecios(RequestEditarEstadoListaPrecios request, int estado) {
        ResponseEditarEstadoListaPrecios rpt = new ResponseEditarEstadoListaPrecios();
        String SQL = "{ call PRODUCTOS.sp_EditarListaPrecio_Estado(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdListaPrecios());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);
            Long empresaId = 1L;
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ListaPrecios actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó ListaPrecios.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarListaPrecio_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
