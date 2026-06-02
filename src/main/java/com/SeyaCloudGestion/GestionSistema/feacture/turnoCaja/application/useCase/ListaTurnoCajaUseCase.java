package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestListaTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseListaTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services.TurnoCajaService;
import org.springframework.stereotype.Component;

@Component
public class ListaTurnoCajaUseCase {
    private final TurnoCajaService turnoCajaService;

    public ListaTurnoCajaUseCase(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }

    public ResponseListaTurnoCaja ListaTurnoCaja(RequestListaTurnoCaja request) {
        try {
            ResponseListaTurnoCaja response = turnoCajaService.ListaTurnoCaja(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaTurnoCaja response = new ResponseListaTurnoCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setTurnoCajas(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los turnos de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaTurnoCaja response = new ResponseListaTurnoCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setTurnoCajas(java.util.List.of());
            return response;
        }
    }
}