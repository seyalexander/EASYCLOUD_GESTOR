package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services.TurnoCajaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleTurnoCajaUseCase {
    private final TurnoCajaService turnoCajaService;

    public DetalleTurnoCajaUseCase(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }

    public ResponseDetalleTurnoCaja DetalleTurnoCaja(long idTurnoCaja) {
        try {
            RequestDetalleTurnoCaja request = new RequestDetalleTurnoCaja();
            request.setIdTurnoCaja(idTurnoCaja);

            ResponseDetalleTurnoCaja response = turnoCajaService.DetalleTurnoCaja(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleTurnoCaja response = new ResponseDetalleTurnoCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al ver el detalle del turno de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleTurnoCaja response = new ResponseDetalleTurnoCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}