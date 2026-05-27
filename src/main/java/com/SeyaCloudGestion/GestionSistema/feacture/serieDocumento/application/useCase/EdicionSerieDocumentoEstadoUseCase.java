package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarEstadoSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseEditarEstadoSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class EdicionSerieDocumentoEstadoUseCase {
    private final SerieDocumentoService serieDocumentoService;

    public EdicionSerieDocumentoEstadoUseCase(SerieDocumentoService serieDocumentoService) {
        this.serieDocumentoService = serieDocumentoService;
    }
    public ResponseEditarEstadoSerieDocumento AnularSerieDocumento(Long idSerieDocumento) {
        try {
            RequestEditarEstadoSeries request = new RequestEditarEstadoSeries();
            request.setIdSeries(idSerieDocumento);
            ResponseEditarEstadoSerieDocumento response = serieDocumentoService.EditarEstadoSerieDocumento(request,0);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoSerieDocumento response = new ResponseEditarEstadoSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el estado de la serie de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoSerieDocumento response = new ResponseEditarEstadoSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoSerieDocumento ActivarSerieDocumento(Long idSerieDocumento) {
        try {
            RequestEditarEstadoSeries request = new RequestEditarEstadoSeries();
            request.setIdSeries(idSerieDocumento);
            ResponseEditarEstadoSerieDocumento response = serieDocumentoService.EditarEstadoSerieDocumento(request,1);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoSerieDocumento response = new ResponseEditarEstadoSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar el estado de la serie de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoSerieDocumento response = new ResponseEditarEstadoSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}