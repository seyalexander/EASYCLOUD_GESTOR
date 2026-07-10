package com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces.IArticulosRegistro;
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
public class ArticulosRegistroRepository implements IArticulosRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroArticulo registrarArticulo(RequestRegistroArticulo request) {
        ResponseRegistroArticulo rpt = new ResponseRegistroArticulo();

        String SQL = "{ call PRODUCTOS.sp_RegistroArticulos(?,?,?,?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            Long empresaId= 1l;
            pstmt.setString(1, request.getImagenUrl());
            pstmt.setString(2, request.getDescripcion());
            pstmt.setString(3, request.getCodigoArticulo());
            //pstmt.setString(4, request.getCodigoBarras());
            pstmt.setDouble(4, request.getPrecioVenta());
            pstmt.setLong(5, request.getIdSubFamilia());
            pstmt.setLong(6, request.getIdUnidadMedida());
            pstmt.setDouble(7, request.getCostoCompra());
            pstmt.setDouble(8, request.getStockMinimo());
            pstmt.setLong(9, request.getIdMarca());
            pstmt.setLong(10, userId);
            pstmt.setLong(11, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Artículo insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el Artículo.");
            }

        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe un articulo con ese codigo.");
            } else {
                rpt.setMessage("Error al registrar el articulo.");
            }
        }

        return rpt;
    }
}
