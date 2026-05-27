package com.SeyaCloudGestion.GestionSistema.feacture.marca.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestRegistroMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseRegistroMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.services.MarcaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroMarcaUseCase {
    private final MarcaService marcaService;

    public RegistroMarcaUseCase(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    public ResponseRegistroMarca RegistroMarca(RequestRegistroMarca request) {
        try {
            ResponseRegistroMarca response = marcaService.RegistroMarca(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroMarca response = new ResponseRegistroMarca();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar la marca: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroMarca response = new ResponseRegistroMarca();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}