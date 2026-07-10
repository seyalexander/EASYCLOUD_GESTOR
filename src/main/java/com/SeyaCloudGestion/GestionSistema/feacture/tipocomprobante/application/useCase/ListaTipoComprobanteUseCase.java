package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestListaTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseListaTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.services.TipoComprobanteService;
import org.springframework.stereotype.Component;

@Component
public class ListaTipoComprobanteUseCase {
    private final TipoComprobanteService tipoComprobanteService;

    public ListaTipoComprobanteUseCase(
            TipoComprobanteService tipoComprobanteService
    ){
        this.tipoComprobanteService = tipoComprobanteService;
    }

    public ResponseListaTipoComprobante ListaTipoComprobante(RequestListaTipoComprobante request) {
        try {
            ResponseListaTipoComprobante response = tipoComprobanteService.listaTipoComprobante(request);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseListaTipoComprobante response = new ResponseListaTipoComprobante();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setTipoComprobante(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar los tipos de comprobante: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaTipoComprobante response = new ResponseListaTipoComprobante();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setTipoComprobante(java.util.List.of());
            return response;
        }
    }
}