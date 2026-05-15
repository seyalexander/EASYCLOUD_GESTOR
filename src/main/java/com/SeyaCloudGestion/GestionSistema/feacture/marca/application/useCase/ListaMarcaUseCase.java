package com.SeyaCloudGestion.GestionSistema.feacture.marca.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services.MarcaService;
import org.springframework.stereotype.Component;

@Component
public class ListaMarcaUseCase {

    private final MarcaService marcaService;

    public ListaMarcaUseCase(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    public ResponseListaMarca listaMarcas(RequestListaMarca request) {
        try {
            ResponseListaMarca response = marcaService.ListaMarca(request);
            if(response.isExito()){

            }

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaMarca response = new ResponseListaMarca();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setMarcas(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las marcas: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaMarca response = new ResponseListaMarca();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setMarcas(java.util.List.of());
            return response;
        }
    }
}
