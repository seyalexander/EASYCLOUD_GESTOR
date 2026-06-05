package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarAllSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseEditarAllSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class EdicionSerieDocumentoUseCase {

    private final SerieDocumentoService serieDocumentoService;

    public EdicionSerieDocumentoUseCase(SerieDocumentoService serieDocumentoService) {
        this.serieDocumentoService = serieDocumentoService;
    }

    public ResponseEditarAllSerieDocumento EdicionAllSerieDocumento(RequestEditarAllSeries request) {
        try {
            ResponseEditarAllSerieDocumento response = serieDocumentoService.EditarAllSerieDocumento(request);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllSerieDocumento response = new ResponseEditarAllSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la serie de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllSerieDocumento response = new ResponseEditarAllSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}