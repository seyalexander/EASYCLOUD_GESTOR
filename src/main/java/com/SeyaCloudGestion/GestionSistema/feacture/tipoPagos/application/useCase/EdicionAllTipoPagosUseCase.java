package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarAllTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseEditarAllTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.services.TipoPagosService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllTipoPagosUseCase {
    private final TipoPagosService tipoPagosService;

    public EdicionAllTipoPagosUseCase(TipoPagosService tipoPagosService) {
        this.tipoPagosService = tipoPagosService;
    }
    public ResponseEditarAllTipoPagos EdicionAllTipoPagos(RequestEditarAllTipoPagos request) {
        try {
            ResponseEditarAllTipoPagos response = tipoPagosService.EditarAllTipoPagos(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllTipoPagos response = new ResponseEditarAllTipoPagos();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el tipo de pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllTipoPagos response = new ResponseEditarAllTipoPagos();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}