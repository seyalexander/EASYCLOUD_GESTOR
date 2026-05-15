package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestListaMonedas;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestListaTipoDocumentos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseListaTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services.TipoDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class ListaTipoDocumentoUseCase {
    private final TipoDocumentoService tipoDocumentoService;

    public ListaTipoDocumentoUseCase(
            TipoDocumentoService tipoDocumentoService
    ){
        this.tipoDocumentoService = tipoDocumentoService;
    }

    public ResponseListaTipoDocumento ListarTipoDocumento(RequestListaTipoDocumentos request) {
        try {
            ResponseListaTipoDocumento response = tipoDocumentoService.ListaTipoDocumento(request);
            if(response.isExito()){

            }

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaTipoDocumento response = new ResponseListaTipoDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setTipoDocumentos(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar los tipos de documentos: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaTipoDocumento response = new ResponseListaTipoDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setTipoDocumentos(java.util.List.of());
            return response;
        }
    }
}
