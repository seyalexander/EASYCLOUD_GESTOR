package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseDetalleCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.services.CajaService;
import org.springframework.stereotype.Component;

@Component
public class DetalleCajaUseCase {

    private final CajaService cajaService;

    public DetalleCajaUseCase(
            CajaService cajaService
    ) {
        this.cajaService = cajaService;
    }

    public ResponseDetalleCaja DetalleCaja(long idCaja) {
        try {
            RequestDetalleCaja request = new RequestDetalleCaja();
            request.setIdCaja(idCaja);
            ResponseDetalleCaja response = cajaService.DetalleCaja(request);
            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleCaja response = new ResponseDetalleCaja();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle de la caja: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleCaja response = new ResponseDetalleCaja();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}