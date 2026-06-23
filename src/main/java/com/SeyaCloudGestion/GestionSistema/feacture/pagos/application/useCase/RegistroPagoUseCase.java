package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.services.PagoService;
import org.springframework.stereotype.Component;

@Component
public class RegistroPagoUseCase {

    private final PagoService pagoService;

    public RegistroPagoUseCase(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    public ResponseRegistroPago registrarPago(RequestRegistroPago request) {
        try {
            ResponseRegistroPago response = pagoService.RegistroPago(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroPago response = new ResponseRegistroPago();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroPago response = new ResponseRegistroPago();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}