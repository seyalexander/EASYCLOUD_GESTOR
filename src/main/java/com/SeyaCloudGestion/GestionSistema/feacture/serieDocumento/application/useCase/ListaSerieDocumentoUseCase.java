package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestListaSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.ResponseListaSerieDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.services.SerieDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class ListaSerieDocumentoUseCase {
    private final SerieDocumentoService serieDocumentoService;

    public ListaSerieDocumentoUseCase(SerieDocumentoService serieDocumentoService) {
        this.serieDocumentoService = serieDocumentoService;
    }
    public ResponseListaSerieDocumento ListaSerieDocumento(RequestListaSeries request) {
        try {
            ResponseListaSerieDocumento response = serieDocumentoService.listaSerieDocumento(request);
            if(response.isExito()){}
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaSerieDocumento response = new ResponseListaSerieDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las series de documento: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaSerieDocumento response = new ResponseListaSerieDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}