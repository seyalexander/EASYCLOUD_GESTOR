package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestAbrirTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseAbrirTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services.TurnoCajaService;
import org.springframework.stereotype.Component;

@Component
public class AbrirTurnoCajaUseCase {
    private final TurnoCajaService turnoCajaService;

    public AbrirTurnoCajaUseCase(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }
    public ResponseAbrirTurnoCaja RegistroTurnoCaja(RequestAbrirTurnoCaja request) {
        try {

            ResponseAbrirTurnoCaja response = turnoCajaService.AbrirTurnoCaja(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseAbrirTurnoCaja response = new ResponseAbrirTurnoCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el turno de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseAbrirTurnoCaja response = new ResponseAbrirTurnoCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}