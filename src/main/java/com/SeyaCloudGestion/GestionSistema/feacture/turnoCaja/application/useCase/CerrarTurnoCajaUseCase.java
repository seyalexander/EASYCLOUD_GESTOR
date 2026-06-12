package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestCerrarTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseCerrarTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services.TurnoCajaService;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import org.springframework.stereotype.Component;

@Component
public class CerrarTurnoCajaUseCase {
    private final TurnoCajaService turnoCajaService;

    public CerrarTurnoCajaUseCase(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }

    public ResponseCerrarTurnoCaja CerrarTurnoCaja(RequestCerrarTurnoCaja request) {
        try {
            //verificar el id
            RequestDetalleTurnoCaja requestDetalle = new RequestDetalleTurnoCaja();
            requestDetalle.setIdTurnoCaja(request.getIdTurnoCaja());

            ResponseDetalleTurnoCaja detalleBD= turnoCajaService.DetalleTurnoCaja(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTurnoCaja() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            //verificar que no este ya cerrado
            if (detalleBD.getTurnoCaja().getEstado() == EstadoCaja.CERRADO) {
                throw new IllegalArgumentException("Este turno ya ha sido cerrado previamente.");
            }
            //realizamos los sets

            double montoSistema =detalleBD.getTurnoCaja().getMontoSistema();
            double diferencia = detalleBD.getTurnoCaja().getMontoSistema()- request.getMontoReal();

            ResponseCerrarTurnoCaja response = turnoCajaService.CerrarTurnoCaja(request,montoSistema,diferencia);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseCerrarTurnoCaja response = new ResponseCerrarTurnoCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el turno de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseCerrarTurnoCaja response = new ResponseCerrarTurnoCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}