package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestRegistrarSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseRegistroSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import org.springframework.stereotype.Component;

@Component
public class RegistroSubFamiliaUseCase {
    private final SubFamiliaService subFamiliaService;

    public RegistroSubFamiliaUseCase(
            SubFamiliaService subFamiliaService
    ){
        this.subFamiliaService = subFamiliaService;
    }

    public ResponseRegistroSubFamilia RegistroSubFamilia(RequestRegistrarSubFamilia request) {
        try {
            ResponseRegistroSubFamilia response = subFamiliaService.RegistroSubFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroSubFamilia response = new ResponseRegistroSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las subfamilias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroSubFamilia response = new ResponseRegistroSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
