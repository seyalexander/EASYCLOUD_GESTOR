package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseDetalleCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.services.CuentasPorCobrarService;
import org.springframework.stereotype.Component;

@Component
public class DetalleCuentasPorCobrarUseCase {
    private final CuentasPorCobrarService cuentasPorCobrarService;

    public DetalleCuentasPorCobrarUseCase(CuentasPorCobrarService cuentasPorCobrarService) {
        this.cuentasPorCobrarService = cuentasPorCobrarService;
    }
    public ResponseDetalleCuentasPorCobrar DetalleCuentasPorCobrar(long idCuentaPorCobrar) {
        try {
            RequestDetalleCuentasPorCobrar request = new RequestDetalleCuentasPorCobrar();
            request.setIdCuentasPorCobrar(idCuentaPorCobrar);

            ResponseDetalleCuentasPorCobrar response = cuentasPorCobrarService.DetalleCuentasPorCobrar(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleCuentasPorCobrar response = new ResponseDetalleCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al ver el detalle de la cuenta por cobrar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleCuentasPorCobrar response = new ResponseDetalleCuentasPorCobrar();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}