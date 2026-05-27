package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestDetalleSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseDetalleSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class DetalleSerieDocumentoUseCase {
    private final SerieDocumentoService serieDocumentoService;
    public DetalleSerieDocumentoUseCase(SerieDocumentoService serieDocumentoService) {
        this.serieDocumentoService = serieDocumentoService;
    }

    public ResponseDetalleSerieDocumento DetalleSerieDocumento(long idSerieDocumento) {
        try {
            RequestDetalleSeries request = new RequestDetalleSeries();
            request.setIdSeries(idSerieDocumento);
            ResponseDetalleSerieDocumento response = serieDocumentoService.DetalleSerieDocumento(request);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleSerieDocumento response = new ResponseDetalleSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al obtener el detalle de la serie de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleSerieDocumento response = new ResponseDetalleSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}