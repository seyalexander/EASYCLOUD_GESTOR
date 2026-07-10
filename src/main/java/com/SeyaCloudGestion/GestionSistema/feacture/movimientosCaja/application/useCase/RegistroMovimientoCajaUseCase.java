package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.services.MovimientoCajaService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.DetalleTipoPagosUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase.DetalleTurnoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import org.springframework.stereotype.Component;

@Component
public class RegistroMovimientoCajaUseCase {
    private final MovimientoCajaService movimientoCajaService;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;
    private final DetalleTipoPagosUseCase detalleTipoPagosUseCase;

    public RegistroMovimientoCajaUseCase(MovimientoCajaService movimientoCajaService, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase, DetalleTipoPagosUseCase detalleTipoPagosUseCase) {
        this.movimientoCajaService = movimientoCajaService;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
        this.detalleTipoPagosUseCase = detalleTipoPagosUseCase;
    }

    public ResponseRegistroMovimientoCaja registroMovimientoCaja(RequestRegistroMovimientoCaja request) {
        try {
            ResponseDetalleTurnoCaja responseBDturnoCaja = detalleTurnoCajaUseCase.DetalleTurnoCaja(request.getIdTurnoCaja(), EstadoCaja.ABIERTO);
            if (!responseBDturnoCaja.isExito() || responseBDturnoCaja.getTurnoCaja() == null) {
                throw new IllegalArgumentException("El turno caja no existe.");
            }
            //validar el tipo de pago
            ResponseDetalleTipoPagos responseBDtipoPago = detalleTipoPagosUseCase.DetalleTipoPagos(request.getIdTipoPago());
            if (!responseBDtipoPago.isExito()|| responseBDtipoPago.getTipoPagos() == null) {
                throw new IllegalArgumentException("No se encontró el tipo de pago con el id proporciano ");
            }

            ResponseRegistroMovimientoCaja response = movimientoCajaService.RegistroMovimientoCaja(request);
            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroMovimientoCaja response = new ResponseRegistroMovimientoCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar el movimiento de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroMovimientoCaja response = new ResponseRegistroMovimientoCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}