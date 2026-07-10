package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestRegistroListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseRegistroListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces.IListaPreciosRegistro;
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
public class ListaPreciosRegistroRepository implements IListaPreciosRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroListaPrecios RegistroListaPrecios(RequestRegistroListaPrecios request) {
        ResponseRegistroListaPrecios rpt = new ResponseRegistroListaPrecios();
        String SQL = "{ call PRODUCTOS.sp_RegistroListaPrecio(?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setString(1,request.getDescripcion());
            Long userId = 1L;
            pstmt.setLong(2, userId);
            Long empresaId = 1L;
            pstmt.setLong(3, empresaId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("ListaPrecios insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó ListaPrecios.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe una lista precio con esa descripción.");
            } else {
                rpt.setMessage("Error al registrar la unidad de medida.");
            }
            log.error("Error en PRODUCTOS.sp_RegistroListaPrecio", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
