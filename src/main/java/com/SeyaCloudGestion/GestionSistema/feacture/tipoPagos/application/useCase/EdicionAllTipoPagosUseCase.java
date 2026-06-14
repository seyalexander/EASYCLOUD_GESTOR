package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarAllTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseEditarAllTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.services.TipoPagosService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.validations.VerificarCambiosTipoPago;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllTipoPagosUseCase {
    private final TipoPagosService tipoPagosService;
    private final VerificarCambiosTipoPago verificarCambiosTipoPago;
    public EdicionAllTipoPagosUseCase(TipoPagosService tipoPagosService, VerificarCambiosTipoPago verificarCambiosTipoPago) {
        this.tipoPagosService = tipoPagosService;
        this.verificarCambiosTipoPago = verificarCambiosTipoPago;
    }
    public ResponseEditarAllTipoPagos EdicionAllTipoPagos(RequestEditarAllTipoPagos request) {
        try {
            RequestDetalleTipoPagos requestDetalle = new RequestDetalleTipoPagos();
            requestDetalle.setIdTipoPago(request.getIdTipoPago());

            ResponseDetalleTipoPagos detalleBD= tipoPagosService.DetalleTipoPagos(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoPagos() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }
            //verificar cambios
            if (!verificarCambiosTipoPago.verificarCambios(detalleBD.getTipoPagos(), request)) {
                throw new ResourceNotFoundException("No se detectaron cambios para actualizar.");
            }

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