package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestRegistroDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseRegistroTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.domain.interfaces.ITransferenciaDetalleRegistro;
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
public class RegistroDetalleTransferenciaRepository implements ITransferenciaDetalleRegistro {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;

    @Override
    public ResponseRegistroTransferenciaDetalle RegistroDetalleTransferencia(long idTransferencia, RequestRegistroDetalleTransferencia request,double costoUnitario) {
        ResponseRegistroTransferenciaDetalle rpt = new ResponseRegistroTransferenciaDetalle();
        String SQL = "{ call INVENTARIO.sp_RegistrarTransferenciaDetalle(?,?,?,?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            Long userId = 1L;
            pstmt.setLong(1, idTransferencia);
            pstmt.setLong(2, request.getIdArticulo());
            pstmt.setDouble(3, request.getCantidad());
            pstmt.setDouble(4, costoUnitario);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                rpt.setExito(true);
                rpt.setMessage("Transferencia detalle insertado correctamente.");
            } else {
                rpt.setExito(false);
                rpt.setMessage("No se insertó el detalle Transferencia.");
            }
        } catch (SQLException e) {
            rpt.setExito(false);
            rpt.setMessage(e.getMessage());
            log.error("Error en INVENTARIO.sp_RegistrarTransferenciaDetalle", e);
        }
        return rpt;
    }

    private void setParameter(CallableStatement pstmt, int index, Object value) throws SQLException {
        pstmt.setObject(index, value);
    }
}
