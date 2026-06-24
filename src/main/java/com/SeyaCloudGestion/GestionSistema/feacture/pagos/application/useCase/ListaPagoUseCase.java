package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.services.PagoService;
import org.springframework.stereotype.Component;

@Component
public class ListaPagoUseCase {
    private final PagoService pagoService;

    public ListaPagoUseCase(
            PagoService pagoService
    ) {
        this.pagoService = pagoService;
    }

    public ResponseListaPago ListaPago(RequestListaPago request) {
        try {
            ResponseListaPago response = new ResponseListaPago();
                    //pagoService.listaPago(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaPago response = new ResponseListaPago();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setPagos(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los pagos: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaPago response = new ResponseListaPago();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setPagos(java.util.List.of());
            return response;
        }
    }
}