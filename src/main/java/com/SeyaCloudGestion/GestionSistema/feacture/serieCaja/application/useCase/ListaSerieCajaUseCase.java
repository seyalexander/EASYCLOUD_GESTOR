package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.domain.services.SerieCajaService;
import org.springframework.stereotype.Component;

@Component
public class ListaSerieCajaUseCase {

    private final SerieCajaService serieCajaService;

    public ListaSerieCajaUseCase(
            SerieCajaService serieCajaService
    ) {
        this.serieCajaService = serieCajaService;
    }

    public ResponseListaSerieCaja ListaSerieCaja(RequestListaSerieCaja request) {
        try {
            ResponseListaSerieCaja response = serieCajaService.listaSerieCaja(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaSerieCaja response = new ResponseListaSerieCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setSerieCajas(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar las series de la caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaSerieCaja response = new ResponseListaSerieCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setSerieCajas(java.util.List.of());
            return response;
        }
    }
}