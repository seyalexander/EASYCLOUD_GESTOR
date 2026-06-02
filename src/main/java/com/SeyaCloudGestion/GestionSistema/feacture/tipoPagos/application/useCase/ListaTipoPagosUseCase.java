package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestListaTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseListaTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.services.TipoPagosService;
import org.springframework.stereotype.Component;

@Component
public class ListaTipoPagosUseCase {
    private final TipoPagosService tipoPagosService;

    public ListaTipoPagosUseCase(TipoPagosService tipoPagosService) {
        this.tipoPagosService = tipoPagosService;
    }

    public ResponseListaTipoPagos ListaTipoPagos(RequestListaTipoPagos request) {
        try {
            ResponseListaTipoPagos response = tipoPagosService.ListaTipoPagos(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaTipoPagos response = new ResponseListaTipoPagos();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setTipoPagos(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los tipos de pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaTipoPagos response = new ResponseListaTipoPagos();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setTipoPagos(java.util.List.of());
            return response;
        }
    }
}