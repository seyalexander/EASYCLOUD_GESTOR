package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestListaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseListaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.services.CajaService;
import org.springframework.stereotype.Component;

@Component
public class ListaCajaUseCase {

    private final CajaService cajaService;

    public ListaCajaUseCase(
            CajaService cajaService
    ) {
        this.cajaService = cajaService;
    }

    public ResponseListaCaja ListaCaja() {
        try {
            ResponseListaCaja response = cajaService.listaCaja();
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaCaja response = new ResponseListaCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setCajas(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las cajas: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaCaja response = new ResponseListaCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setCajas(java.util.List.of());
            return response;
        }
    }
}