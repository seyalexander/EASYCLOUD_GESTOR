package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services.TurnoCajaService;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import org.springframework.stereotype.Component;

@Component
public class DetalleTurnoCajaUseCase {
    private final TurnoCajaService turnoCajaService;

    public DetalleTurnoCajaUseCase(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }

    public ResponseDetalleTurnoCaja DetalleTurnoCaja(long idCaja, EstadoCaja estadoCaja) {
        try {
            RequestDetalleTurnoCaja request = new RequestDetalleTurnoCaja();
            request.setIdCaja(idCaja);

            ResponseDetalleTurnoCaja response = turnoCajaService.DetalleTurnoCaja(request,estadoCaja);

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