package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestDetalleCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseDetalleCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.services.CuentasPorPagarService;
import org.springframework.stereotype.Component;

@Component
public class DetalleCuentasPorPagarUseCase {

    private final CuentasPorPagarService cuentasPorPagarService;

    public DetalleCuentasPorPagarUseCase(
            CuentasPorPagarService cuentasPorPagarService
    ) {
        this.cuentasPorPagarService = cuentasPorPagarService;
    }

    public ResponseDetalleCuentasPorPagar detalleCuentaPorPagar(long idCuentaPorPagar) {
        try {
            RequestDetalleCuentasPorPagar request = new RequestDetalleCuentasPorPagar();
            request.setIdCuentasPorPagar(idCuentaPorPagar);

            ResponseDetalleCuentasPorPagar response = cuentasPorPagarService.DetalleCuentasPorPagar(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleCuentasPorPagar response = new ResponseDetalleCuentasPorPagar();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle de la cuenta por pagar: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleCuentasPorPagar response = new ResponseDetalleCuentasPorPagar();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}