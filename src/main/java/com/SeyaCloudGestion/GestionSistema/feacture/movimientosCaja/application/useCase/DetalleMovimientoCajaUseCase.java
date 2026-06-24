package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestDetalleMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseDetalleMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.services.MovimientoCajaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleMovimientoCajaUseCase {

    private final MovimientoCajaService movimientoCajaService;

    public DetalleMovimientoCajaUseCase(MovimientoCajaService movimientoCajaService) {
        this.movimientoCajaService = movimientoCajaService;
    }

    public ResponseDetalleMovimientoCaja DetalleMovimientoCaja(long idMovimientoCaja, long idTurnoCaja) {
        try {
            RequestDetalleMovimientoCaja request = new RequestDetalleMovimientoCaja();
            request.setIdMovimientoCaja(idMovimientoCaja);
            request.setIdTurnoCaja(idTurnoCaja);

            ResponseDetalleMovimientoCaja response = movimientoCajaService.DetalleMovimientoCaja(request);
            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleMovimientoCaja response = new ResponseDetalleMovimientoCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle del movimiento de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleMovimientoCaja response = new ResponseDetalleMovimientoCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}