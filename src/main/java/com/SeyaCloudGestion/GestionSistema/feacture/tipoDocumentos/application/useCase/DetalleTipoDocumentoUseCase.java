package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services.TipoDocumentoService;
import org.springframework.stereotype.Component;

@Component
public class DetalleTipoDocumentoUseCase {
    private final TipoDocumentoService tipoDocumentoService;

    public DetalleTipoDocumentoUseCase(
            TipoDocumentoService tipoDocumentoService
    ){
        this.tipoDocumentoService = tipoDocumentoService;
    }

    public ResponseDetalleTipoDocumento DetalleTipoDocumento(long idTipoDocumento) {
        try {
            RequestDetalleTipoDocumento request =  new RequestDetalleTipoDocumento();
            request.setIdTipoDocumentos(idTipoDocumento);
            ResponseDetalleTipoDocumento response = tipoDocumentoService.DetalleTipoDocumento(request);
            if(response.isExito()){

            }

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleTipoDocumento response = new ResponseDetalleTipoDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setTipoDocumento(null);
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar los tipos de documentos: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleTipoDocumento response = new ResponseDetalleTipoDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setTipoDocumento(null);
            return response;
        }
    }
}
