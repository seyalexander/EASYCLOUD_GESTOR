package com.SeyaCloudGestion.GestionSistema.feacture.marca.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services.MarcaService;
import org.springframework.stereotype.Component;

@Component
public class EdicionMarcaUseCase {
    private final  MarcaService marcaService;

    public EdicionMarcaUseCase(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    public ResponseEditarAllMarca EdicionAllMarca(RequestEditarAllMarca request) {
        try {
            ResponseEditarAllMarca response = marcaService.EditarAllMarca(request);
            if(response.isExito()){

            }

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllMarca response = new ResponseEditarAllMarca();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar la marca: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllMarca response = new ResponseEditarAllMarca();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}