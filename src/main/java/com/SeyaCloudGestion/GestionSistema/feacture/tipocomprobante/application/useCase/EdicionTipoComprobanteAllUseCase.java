package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestEditarAllTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseEditarAllTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.services.TipoComprobanteService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.validations.VerificarCambiosTipoComprobante;
import org.springframework.stereotype.Component;

@Component
public class EdicionTipoComprobanteAllUseCase {

    private final TipoComprobanteService tipoComprobanteService;
    private final VerificarCambiosTipoComprobante verificarCambiosTipoComprobante;

    public EdicionTipoComprobanteAllUseCase(
            TipoComprobanteService tipoComprobanteService,
            VerificarCambiosTipoComprobante verificarCambiosTipoComprobante
    ){
        this.tipoComprobanteService = tipoComprobanteService;
        this.verificarCambiosTipoComprobante = verificarCambiosTipoComprobante;
    }

    public ResponseEditarAllTipoComprobante EdicionAllTipoComprobante(RequestEditarAllTipoComprobante request) {
        try {

            RequestDetalleTipoComprobante requestDetalle = new RequestDetalleTipoComprobante();
            requestDetalle.setIdTipoComprobante(request.getIdTipoComprobante());

            ResponseDetalleTipoComprobante detalleBD = tipoComprobanteService.DetalleTipoComprobante(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoCompobante() == null) {
                throw new IllegalArgumentException("El tipo de comprobante no existe.");
            }

            if (!verificarCambiosTipoComprobante.verificarCambios(detalleBD.getTipoCompobante(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }

            ResponseEditarAllTipoComprobante response = tipoComprobanteService.EditarAllTipoComprobante(request);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllTipoComprobante response = new ResponseEditarAllTipoComprobante();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar el tipo de comprobante: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllTipoComprobante response = new ResponseEditarAllTipoComprobante();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}