package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestRegistroTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseRegistroTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.services.TipoPagosService;
import org.springframework.stereotype.Component;

@Component
public class RegistroTipoPagosUseCase {
    private final TipoPagosService tipoPagosService;

    public RegistroTipoPagosUseCase(TipoPagosService tipoPagosService) {
        this.tipoPagosService = tipoPagosService;
    }
    public ResponseRegistroTipoPagos RegistroTipoPagos(RequestRegistroTipoPagos request) {
        try {
            ResponseRegistroTipoPagos response = tipoPagosService.RegistroTipoPagos(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroTipoPagos response = new ResponseRegistroTipoPagos();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el tipo de pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroTipoPagos response = new ResponseRegistroTipoPagos();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}