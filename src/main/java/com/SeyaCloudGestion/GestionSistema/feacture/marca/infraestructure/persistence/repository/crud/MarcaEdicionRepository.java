package com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestEditarEstadoMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseEditarEstadoMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.interfaces.IMarcaEdicion;
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
public class MarcaEdicionRepository implements IMarcaEdicion {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseEditarAllMarca EditarAllMarca(RequestEditarAllMarca request) {
        ResponseEditarAllMarca rpt = new ResponseEditarAllMarca();
        String SQL = "{ call PRODUCTOS.sp_EditarMarca(?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {
            setParameter(pstmt, 1, request.getIdMarca());
            setParameter(pstmt, 2, request.getDescripcion());
            setParameter(pstmt, 3, request.getImagenUrl());
            setParameter(pstmt, 4, request.getEstado());
            Long userId = 1L;
            pstmt.setLong(5, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Marca actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Marca.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarMarca", e);
        }
        return rpt;
    }

    @Override
    public ResponseEditarEstadoMarca EditarEstadoMarca(RequestEditarEstadoMarca request, int estado) {
        ResponseEditarEstadoMarca rpt = new ResponseEditarEstadoMarca();
        String SQL = "{ call PRODUCTOS.sp_EditarMarca_Estado(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getIdMarca());
            pstmt.setInt(2, estado);
            Long userId = 1L;
            pstmt.setLong(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Marca actualizado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se actualizó Marca.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en PRODUCTOS.sp_EditarMarca_Estado", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
