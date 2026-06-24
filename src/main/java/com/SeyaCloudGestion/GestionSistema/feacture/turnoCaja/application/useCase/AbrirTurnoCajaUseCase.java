package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase.DetalleCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestDetalleSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestAbrirTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseAbrirTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.services.TurnoCajaService;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import org.apache.coyote.Response;
import org.springframework.stereotype.Component;

@Component
public class AbrirTurnoCajaUseCase {
    private final TurnoCajaService turnoCajaService;
    private final DetalleCajaUseCase detalleCajaUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;

    public AbrirTurnoCajaUseCase(TurnoCajaService turnoCajaService, DetalleCajaUseCase detalleCajaUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase) {
        this.turnoCajaService = turnoCajaService;
        this.detalleCajaUseCase = detalleCajaUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
    }

    public ResponseAbrirTurnoCaja RegistroTurnoCaja(RequestAbrirTurnoCaja request) {
        try {
            ResponseDetalleCaja responseBDcaja= detalleCajaUseCase.DetalleCaja(request.getIdCaja());
            if (!responseBDcaja.isExito() || responseBDcaja.getCaja() == null) {
                throw new IllegalArgumentException("El la caja no existe.");
            }
            //verificar que no tenga un turno ya abierto
            EstadoCaja estado = EstadoCaja.ABIERTO;
            ResponseDetalleTurnoCaja responseBDturnoCaja = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdCaja(),estado);
            if (responseBDturnoCaja.isExito() && responseBDturnoCaja.getTurnoCaja() != null) {
                throw new IllegalArgumentException("La caja ya contiene un turno Abierto");
            }

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