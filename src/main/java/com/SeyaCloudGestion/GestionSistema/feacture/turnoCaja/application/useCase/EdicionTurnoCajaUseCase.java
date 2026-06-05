package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestEditarAllTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseEditarAllTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services.TurnoCajaService;
import org.springframework.stereotype.Component;

@Component
public class EdicionTurnoCajaUseCase {
    private final TurnoCajaService turnoCajaService;

    public EdicionTurnoCajaUseCase(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }

    public ResponseEditarAllTurnoCaja EdicionAllTurnoCaja(RequestEditarAllTurnoCaja request) {
        try {
            ResponseEditarAllTurnoCaja response = turnoCajaService.EditarAllTurnoCaja(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllTurnoCaja response = new ResponseEditarAllTurnoCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el turno de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllTurnoCaja response = new ResponseEditarAllTurnoCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}