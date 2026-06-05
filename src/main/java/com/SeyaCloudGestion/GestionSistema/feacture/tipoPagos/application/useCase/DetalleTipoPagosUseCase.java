package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.services.TipoPagosService;
import org.springframework.stereotype.Component;

@Component
public class DetalleTipoPagosUseCase {
    private final TipoPagosService tipoPagosService;

    public DetalleTipoPagosUseCase(TipoPagosService tipoPagosService) {
        this.tipoPagosService = tipoPagosService;
    }
    public ResponseDetalleTipoPagos DetalleTipoPagos(long idTipoPago) {
        try {
            RequestDetalleTipoPagos request = new RequestDetalleTipoPagos();
            request.setIdTipoPago(idTipoPago);

            ResponseDetalleTipoPagos response = tipoPagosService.DetalleTipoPagos(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleTipoPagos response = new ResponseDetalleTipoPagos();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al ver el detalle del tipo de pago: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleTipoPagos response = new ResponseDetalleTipoPagos();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
