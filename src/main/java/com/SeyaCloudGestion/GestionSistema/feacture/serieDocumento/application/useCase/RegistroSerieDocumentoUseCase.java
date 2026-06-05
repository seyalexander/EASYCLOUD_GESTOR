package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestRegistroSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseRegistroSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class RegistroSerieDocumentoUseCase {
    private final SerieDocumentoService serieDocumentoService;

    public RegistroSerieDocumentoUseCase(SerieDocumentoService serieDocumentoService) {
        this.serieDocumentoService = serieDocumentoService;
    }
    public ResponseRegistroSerieDocumento RegistroSerieDocumento(RequestRegistroSeries request) {
        try {
            ResponseRegistroSerieDocumento response = serieDocumentoService.RegistroSerieDocumento(request);
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