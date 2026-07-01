package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestRegistroProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.ResponseRegistroProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.interfaces.IProveedoresRegistro;
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
public class ProveedoresRegistroRepository implements IProveedoresRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroProveedor RegistroProveedores(RequestRegistroProveedor request) {
        ResponseRegistroProveedor rpt = new ResponseRegistroProveedor();
        String SQL = "{ call COMPRAS.sp_RegistroProveedor(?,?,?,?,?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            setParameter(pstmt, 1, request.getRazonSocial());
            setParameter(pstmt, 2, request.getRuc());
            setParameter(pstmt, 3, request.getEmail());
            setParameter(pstmt, 4, request.getTelefono());
            setParameter(pstmt, 5, request.getDireccion());
            setParameter(pstmt, 6, request.getIdTipoDocumento());
            Long empresaId = 1L;
            pstmt.setLong(7, empresaId);
            Long userId = 1L;
            pstmt.setLong(8, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Proveedores insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó Proveedores.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                rpt.setMessage("Ya existe este proveedor .");
            } else {
                rpt.setMessage("Error al registrar el proveedor.");
            }
            log.error("Error en COMPRAS.sp_RegistroProveedores", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
