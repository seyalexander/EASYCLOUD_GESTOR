package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase.DetalleCajaUseCase;
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
    private final DetalleCajaUseCase detalleCajaUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;

    public CerrarTurnoCajaUseCase(TurnoCajaService turnoCajaService, DetalleCajaUseCase detalleCajaUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase) {
        this.turnoCajaService = turnoCajaService;
        this.detalleCajaUseCase = detalleCajaUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
    }

    public ResponseCerrarTurnoCaja CerrarTurnoCaja(RequestCerrarTurnoCaja request) {
        try {
            ResponseDetalleCaja responseBDcaja= detalleCajaUseCase.DetalleCaja(request.getIdCaja());
            if (!responseBDcaja.isExito() || responseBDcaja.getCaja() == null) {
                throw new IllegalArgumentException("El la caja no existe.");
            }
            ResponseDetalleTurnoCaja detalleBD = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdCaja(), EstadoCaja.ABIERTO);

            if (!detalleBD.isExito() || detalleBD.getTurnoCaja() == null) {
                throw new IllegalArgumentException("No hay ningún turno abierto para esta caja.");
            }

            if (detalleBD.getTurnoCaja().getEstado().equals(EstadoCaja.CERRADO)) {
                throw new IllegalArgumentException("No se puede volver a cerrar una caja ya cerrada");
            }

            //realizamos los sets
            double montoSistema =detalleBD.getTurnoCaja().getMontoSistema();
            double diferencia =  request.getMontoReal()-detalleBD.getTurnoCaja().getMontoSistema();

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