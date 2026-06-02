package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestRegistroTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseRegistroTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services.TurnoCajaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroTurnoCajaUseCase {
    private final TurnoCajaService turnoCajaService;

    public RegistroTurnoCajaUseCase(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }
    public ResponseRegistroTurnoCaja RegistroTurnoCaja(RequestRegistroTurnoCaja request) {
        try {
            ResponseRegistroTurnoCaja response = turnoCajaService.RegistroTurnoCaja(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroTurnoCaja response = new ResponseRegistroTurnoCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el turno de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroTurnoCaja response = new ResponseRegistroTurnoCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}