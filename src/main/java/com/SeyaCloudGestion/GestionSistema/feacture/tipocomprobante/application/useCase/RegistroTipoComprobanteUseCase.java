package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestRegistroTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseRegistroTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.services.TipoComprobanteService;
import org.springframework.stereotype.Component;

@Component
public class RegistroTipoComprobanteUseCase {
    private final TipoComprobanteService tipoComprobanteService;

    public RegistroTipoComprobanteUseCase(
            TipoComprobanteService tipoComprobanteService
    ){
        this.tipoComprobanteService = tipoComprobanteService;
    }

    public ResponseRegistroTipoComprobante RegistroTipoComprobante(RequestRegistroTipoComprobante request) {
        try {
            ResponseRegistroTipoComprobante response = tipoComprobanteService.RegistroTipoComprobante(request);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroTipoComprobante response = new ResponseRegistroTipoComprobante();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar los tipos de comprobante: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroTipoComprobante response = new ResponseRegistroTipoComprobante();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}