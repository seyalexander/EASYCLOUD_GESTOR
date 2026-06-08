package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestEditarAllUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestEditarEstadoUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseEditarAllUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseEditarEstadoUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.domain.interfaces.IUnidadMedidaEdicion;
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
public class UnidadMedidaEdicionRepository implements IUnidadMedidaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllUnidadMedida EditarAllUnidadMedida(RequestEditarAllUnidadMedida request) {
        ResponseEditarAllUnidadMedida rpt = new ResponseEditarAllUnidadMedida();
        String SQL = "{ call PRODUCTOS.sp_EditarUnidadMedida(?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdUnidadMedida());
            setParameter(pstmt, 2, request.getDescripcion());
            setParameter(pstmt, 3, request.getSiglas());
            setParameter(pstmt, 4, request.getEstado());
            Long userId = 1L;
            Long empresaId = 1L;
            pstmt.setLong(5, userId);
            pstmt.setLong(6, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("UnidadMedida actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó UnidadMedida.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarUnidadMedida", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoUnidadMedida EditarEstadoUnidadMedida(RequestEditarEstadoUnidadMedida request, int estado) {
        ResponseEditarEstadoUnidadMedida rpt = new ResponseEditarEstadoUnidadMedida();
        String SQL = "{ call PRODUCTOS.sp_EditarUnidadMedida_Estado(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdUnidadMedida());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            Long empresaId = 1L;
            pstmt.setLong(3, userId);
            pstmt.setLong(4, empresaId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("UnidadMedida actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó UnidadMedida.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarUnidadMedida_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
