package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.services.TipoPagosService;
import org.springframework.stereotype.Component;

@Component
public class EdicionTipoPagosEstadoUseCase {
    private final TipoPagosService tipoPagosService;

    public EdicionTipoPagosEstadoUseCase(TipoPagosService tipoPagosService) {
        this.tipoPagosService = tipoPagosService;
    }
    public ResponseEditarEstadoTipoPagos AnularTipoPagos(long idTipoPago) {
        try {
            RequestEditarEstadoTipoPagos request = new RequestEditarEstadoTipoPagos();
            request.setIdTipoPago(idTipoPago);

            ResponseEditarEstadoTipoPagos response = tipoPagosService.EditarEstadoTipoPagos(request, 0);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoTipoPagos response = new ResponseEditarEstadoTipoPagos();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al anular el tipo de pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoTipoPagos response = new ResponseEditarEstadoTipoPagos();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoTipoPagos ActivarTipoPagos(long idTipoPago) {
        try {
            RequestEditarEstadoTipoPagos request = new RequestEditarEstadoTipoPagos();
            request.setIdTipoPago(idTipoPago);

            ResponseEditarEstadoTipoPagos response = tipoPagosService.EditarEstadoTipoPagos(request, 1);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoTipoPagos response = new ResponseEditarEstadoTipoPagos();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al activar el tipo de pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoTipoPagos response = new ResponseEditarEstadoTipoPagos();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}