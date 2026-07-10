package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase;
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
    private final DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase;
    public EdicionTipoComprobanteAllUseCase(
            TipoComprobanteService tipoComprobanteService,
            VerificarCambiosTipoComprobante verificarCambiosTipoComprobante, DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase
    ){
        this.tipoComprobanteService = tipoComprobanteService;
        this.verificarCambiosTipoComprobante = verificarCambiosTipoComprobante;
        this.detalleTipoComprobanteUseCase = detalleTipoComprobanteUseCase;
    }

    public ResponseEditarAllTipoComprobante EdicionAllTipoComprobante(RequestEditarAllTipoComprobante request) {
        try {

            // get id
            ResponseDetalleTipoComprobante detalleBDTipoComprobante= detalleTipoComprobanteUseCase.DetalleTipoComprobante(request.getIdTipoComprobante());

            if (!detalleBDTipoComprobante.isExito() || detalleBDTipoComprobante.getTipoCompobante() == null) {
                throw new IllegalArgumentException("El tipo de comprobante no existe.");
            }

            if (!verificarCambiosTipoComprobante.verificarCambios(detalleBDTipoComprobante.getTipoCompobante(), request)) {
                throw new IllegalArgumentException("No se detectaron cambios para actualizar.");
            }
            // verificar cambios
            if (!verificarCambiosTipoComprobante.verificarCambios(detalleBDTipoComprobante.getTipoCompobante(), request)) {
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