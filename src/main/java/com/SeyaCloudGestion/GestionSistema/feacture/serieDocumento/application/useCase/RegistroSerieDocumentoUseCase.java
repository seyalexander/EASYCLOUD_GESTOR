package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestRegistroSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseRegistroSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.services.TipoComprobanteService;
import org.springframework.stereotype.Component;

@Component
public class RegistroSerieDocumentoUseCase {
    private final SerieDocumentoService serieDocumentoService;
    private final TipoComprobanteService tipoComprobanteService;

    public RegistroSerieDocumentoUseCase(SerieDocumentoService serieDocumentoService, TipoComprobanteService tipoComprobanteService) {
        this.serieDocumentoService = serieDocumentoService;
        this.tipoComprobanteService = tipoComprobanteService;
    }
    public ResponseRegistroSerieDocumento RegistroSerieDocumento(RequestRegistroSeries request) {
        try {
            //verificar el id del tipo comporbante
            // get id
            RequestDetalleTipoComprobante requestDetalle = new RequestDetalleTipoComprobante();
            requestDetalle.setIdTipoComprobante(request.getIdTipoComprobante());

            ResponseDetalleTipoComprobante detalleBD = tipoComprobanteService.DetalleTipoComprobante(requestDetalle);

            if (!detalleBD.isExito() || detalleBD.getTipoCompobante() == null) {
                throw new IllegalArgumentException("El tipo de comprobante no existe.");
            }
            // creamos el correlativo
            long correlativoInicial = 0;
            ResponseRegistroSerieDocumento response = serieDocumentoService.RegistroSerieDocumento(request,correlativoInicial);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroSerieDocumento response = new ResponseRegistroSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar la serie de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroSerieDocumento response = new ResponseRegistroSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}