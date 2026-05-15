package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import org.springframework.stereotype.Component;

@Component
public class EdicionSubFamiliaAllUseCase {
    private final SubFamiliaService subFamiliaService;

    public EdicionSubFamiliaAllUseCase(
            SubFamiliaService subFamiliaService
    ){
        this.subFamiliaService = subFamiliaService;
    }

    public ResponseEditarAllSubFamilia EdicionAllFamilia(RequestEditarAllSubFamilia request) {
        try {
            ResponseEditarAllSubFamilia response = subFamiliaService.EdicionAllSubFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllSubFamilia response = new ResponseEditarAllSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la sub familia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllSubFamilia response = new ResponseEditarAllSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
