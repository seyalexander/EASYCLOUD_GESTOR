package com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.repository.crud;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseDetalleEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.model.EmpresaModel;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestDetalleMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseDetalleMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces.IMonedaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.model.MonedaModel;
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

@Slf4j
@Repository
@Transactional("sqlServerTransactionManager")
public class MonedaDetalleRepository implements IMonedaDetalle {

    @Autowired
    @Qualifier("SQLSERVER")
    private DataSource con;


    @Override
    public ResponseDetalleMoneda DetalleMoneda(RequestDetalleMoneda request) {
        ResponseDetalleMoneda response = new ResponseDetalleMoneda();
        MonedaModel moneda = null;

        String SQL = "{ call CONFIGURACION.sp_ObtenerMonedaPorId(?) }";

        try (Connection conn = con.getConnection();
             CallableStatement pstmt = conn.prepareCall(SQL)) {

            pstmt.setLong(1, request.getIdMoneda());

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    moneda = new MonedaModel();
                    moneda.setIdMoneda(rs.getLong("idMoneda"));
                    moneda.setDescripcion(rs.getString("descripcion"));
                    moneda.setSimbolo(rs.getString("simbolo"));
                    moneda.setEsPrincipal(rs.getInt("esPrincipal"));
                    moneda.setEstado(rs.getInt("estado"));
                    moneda.setFechaCreacion(
                            rs.getTimestamp("fechaCreacion") != null
                                    ? rs.getTimestamp("fechaCreacion").toLocalDateTime()
                                    : null
                    );

                    moneda.setFechaEdicion(
                            rs.getTimestamp("fechaEdicion") != null
                                    ? rs.getTimestamp("fechaEdicion").toLocalDateTime()
                                    : null
                    );

                    moneda.setFechaAnulacion(
                            rs.getTimestamp("fechaAnulacion") != null
                                    ? rs.getTimestamp("fechaAnulacion").toLocalDateTime()
                                    : null
                    );
                    moneda.setIdUsuarioCreacion(rs.getLong("idUsuarioCreacion"));
                    moneda.setIdUsuarioEdicion(rs.getLong("idUsuarioEdicion"));
                    moneda.setIdUsuarioAnulacion(rs.getLong("idUsuarioAnulacion"));

                    response.setExito(true);
                    response.setMessage("Moneda obtenido correctamente");
                    response.setMoneda(moneda);

                } else {
                    response.setExito(false);
                    response.setMessage("No se encontró a la moneda");
                }
            }

        } catch (SQLException e) {
            response.setExito(false);
            response.setMessage("Error al obtener la Tipo Documento");
            log.error("Error en CONFIGURACION.sp_ObtenerTipoDocumentoPorId", e);
        }

        return response;
    }
}
