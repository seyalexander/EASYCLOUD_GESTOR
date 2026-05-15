package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestRegistroFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseRegistroFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services.FamiliaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroFamiliaUseCase {
    private final FamiliaService familiaService;

    public RegistroFamiliaUseCase(
            FamiliaService familiaService
    ){
        this.familiaService = familiaService;
    }

    public ResponseRegistroFamilia RegistroFamilia(RequestRegistroFamilia request) {
        try {
            ResponseRegistroFamilia response = familiaService.RegistroFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroFamilia response = new ResponseRegistroFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroFamilia response = new ResponseRegistroFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
