package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services.FamiliaService;
import org.springframework.stereotype.Component;

@Component
public class EdicionFamiliaUseCase {

    private final FamiliaService familiaService;

    public EdicionFamiliaUseCase(
            FamiliaService familiaService
    ){
        this.familiaService = familiaService;
    }

    public ResponseEditarAllFamilia EdicionAllFamilia(RequestEditarAllFamilia request) {
        try {
            ResponseEditarAllFamilia response = familiaService.EditarAllFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllFamilia response = new ResponseEditarAllFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllFamilia response = new ResponseEditarAllFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
