package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.services.TipoComprobanteService;
import org.springframework.stereotype.Component;

@Component
public class DetalleTipoComprobanteUseCase {

    private final TipoComprobanteService tipoComprobanteService;

    public DetalleTipoComprobanteUseCase(
            TipoComprobanteService tipoComprobanteService
    ){
        this.tipoComprobanteService = tipoComprobanteService;
    }

    public ResponseDetalleTipoComprobante DetalleTipoComprobante(long idTipoComprobante) {
        try {
            RequestDetalleTipoComprobante request = new RequestDetalleTipoComprobante();
            request.setIdTipoComprobante(idTipoComprobante);

            ResponseDetalleTipoComprobante response = tipoComprobanteService.DetalleTipoComprobante(request);
            if(response.isExito()){}

            return response;

        } catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleTipoComprobante response = new ResponseDetalleTipoComprobante();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al obtener el detalle del tipo de comprobante: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleTipoComprobante response = new ResponseDetalleTipoComprobante();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}