package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseDetalleTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.ResponseEditarEstadoTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.services.TipoPagosService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionTipoPagosEstadoUseCase {
    private final TipoPagosService tipoPagosService;

    public EdicionTipoPagosEstadoUseCase(TipoPagosService tipoPagosService) {
        this.tipoPagosService = tipoPagosService;
    }
    public ResponseEditarEstadoTipoPagos AnularTipoPagos(long idTipoPago) {
        try {
            RequestDetalleTipoPagos requestDetalle = new RequestDetalleTipoPagos();
            requestDetalle.setIdTipoPago(idTipoPago);

            ResponseDetalleTipoPagos detalleBD= tipoPagosService.DetalleTipoPagos(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoPagos() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getTipoPagos().getEstado(), 0)) {
                throw new IllegalArgumentException("El tipo pago ya se encuentra anulado.");
            }

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
            RequestDetalleTipoPagos requestDetalle = new RequestDetalleTipoPagos();
            requestDetalle.setIdTipoPago(idTipoPago);

            ResponseDetalleTipoPagos detalleBD= tipoPagosService.DetalleTipoPagos(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoPagos() == null) {
                throw new ResourceNotFoundException("El Id no existe.");
            }

            if (Objects.equals(detalleBD.getTipoPagos().getEstado(), 1)) {
                throw new IllegalArgumentException("El tipo pago ya se encuentra Activado.");
            }

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