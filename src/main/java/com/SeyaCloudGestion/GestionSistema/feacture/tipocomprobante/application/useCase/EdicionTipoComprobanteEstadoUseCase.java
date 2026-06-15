package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase;// Generado a partir de la arquitectura de subFamilia.

import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestEditarEstadoTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseEditarEstadoTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.services.TipoComprobanteService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.validations.VerificarCambiosTipoComprobante;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EdicionTipoComprobanteEstadoUseCase {

    private final TipoComprobanteService tipoComprobanteService;
    private final VerificarCambiosTipoComprobante verificarCambiosTipoComprobante;

    public EdicionTipoComprobanteEstadoUseCase(
            TipoComprobanteService tipoComprobanteService,
            VerificarCambiosTipoComprobante verificarCambiosTipoComprobante
    ){
        this.tipoComprobanteService = tipoComprobanteService;
        this.verificarCambiosTipoComprobante = verificarCambiosTipoComprobante;
    }

    public ResponseEditarEstadoTipoComprobante EdicionAnularTipoComprobante(long idTipoComprobante) {
        try {
            // get id
            RequestDetalleTipoComprobante requestDetalle = new RequestDetalleTipoComprobante();
            requestDetalle.setIdTipoComprobante(idTipoComprobante);

            ResponseDetalleTipoComprobante detalleBD = tipoComprobanteService.DetalleTipoComprobante(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoCompobante() == null) {
                throw new IllegalArgumentException("El tipo de comprobante no existe.");
            }

            // verificar
            if (Objects.equals(detalleBD.getTipoCompobante().getEstado(), 0)) {
                throw new IllegalArgumentException("El tipo de comprobante ya se encuentra anulado.");
            }

            RequestEditarEstadoTipoComprobante request = new RequestEditarEstadoTipoComprobante();
            request.setIdTipoComprobante(idTipoComprobante);

            ResponseEditarEstadoTipoComprobante response = tipoComprobanteService.EditarEstadoTipoComprobante(request, 0);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoTipoComprobante response = new ResponseEditarEstadoTipoComprobante();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e){
            String mensajeError = "Error inesperado al editar el estado del tipo de comprobante: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoTipoComprobante response = new ResponseEditarEstadoTipoComprobante();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoTipoComprobante EdicionActivarTipoComprobante(long idTipoComprobante) {
        try {
            // get id
            RequestDetalleTipoComprobante requestDetalle = new RequestDetalleTipoComprobante();
            requestDetalle.setIdTipoComprobante(idTipoComprobante);

            ResponseDetalleTipoComprobante detalleBD = tipoComprobanteService.DetalleTipoComprobante(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoCompobante() == null) {
                throw new IllegalArgumentException("El tipo de comprobante no existe.");
            }

            // verificar
            if (Objects.equals(detalleBD.getTipoCompobante().getEstado(), 1)) {
                throw new IllegalArgumentException("El tipo de comprobante ya se encuentra activado.");
            }

            RequestEditarEstadoTipoComprobante request = new RequestEditarEstadoTipoComprobante();
            request.setIdTipoComprobante(idTipoComprobante);

            ResponseEditarEstadoTipoComprobante response = tipoComprobanteService.EditarEstadoTipoComprobante(request, 1);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoTipoComprobante response = new ResponseEditarEstadoTipoComprobante();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e){
            String mensajeError = "Error inesperado al editar el estado del tipo de comprobante: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoTipoComprobante response = new ResponseEditarEstadoTipoComprobante();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}