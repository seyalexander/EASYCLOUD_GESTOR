package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestEditarEstadoCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseEditarEstadoCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services.CuentasPorCobrarService;
import org.springframework.stereotype.Component;

@Component
public class EdicionCuentasPorCobrarEstadoUseCase {
    private final CuentasPorCobrarService cuentasPorCobrarService;

    public EdicionCuentasPorCobrarEstadoUseCase(CuentasPorCobrarService cuentasPorCobrarService) {
        this.cuentasPorCobrarService = cuentasPorCobrarService;
    }
    public ResponseEditarEstadoCuentasPorCobrar CancelarCuentasPorCobrar(long idCuentaPorCobrar) {
        try {
            RequestEditarEstadoCuentasPorCobrar request = new RequestEditarEstadoCuentasPorCobrar();
            request.setIdCuentasPorCobrar(idCuentaPorCobrar);
            String estado= "CANCELADA";
            ResponseEditarEstadoCuentasPorCobrar response =
                    cuentasPorCobrarService.EditarEstadoCuentasPorCobrar(request, estado);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoCuentasPorCobrar response = new ResponseEditarEstadoCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al Candelar la cuenta por cobrar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoCuentasPorCobrar response = new ResponseEditarEstadoCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}