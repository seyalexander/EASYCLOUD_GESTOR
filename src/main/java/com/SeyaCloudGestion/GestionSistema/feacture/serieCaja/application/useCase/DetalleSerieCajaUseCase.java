package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestDetalleSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseDetalleSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.domain.services.SerieCajaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleSerieCajaUseCase {

    private final SerieCajaService serieCajaService;

    public DetalleSerieCajaUseCase(
            SerieCajaService serieCajaService
    ) {
        this.serieCajaService = serieCajaService;
    }

    public ResponseDetalleSerieCaja DetalleSerieCaja(long idSerieCaja) {
        try {
            RequestDetalleSerieCaja request = new RequestDetalleSerieCaja();
            request.setIdSerieCaja(idSerieCaja);

            ResponseDetalleSerieCaja response = serieCajaService.DetalleSerieCaja(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleSerieCaja response = new ResponseDetalleSerieCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle de la serie de caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleSerieCaja response = new ResponseDetalleSerieCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}