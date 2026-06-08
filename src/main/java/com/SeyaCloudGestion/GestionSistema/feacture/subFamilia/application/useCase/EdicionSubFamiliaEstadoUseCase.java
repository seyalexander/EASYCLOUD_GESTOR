package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarEstadoSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseEditarEstadoSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import org.springframework.stereotype.Component;

@Component
public class EdicionSubFamiliaEstadoUseCase {

    private final SubFamiliaService subFamiliaService;

    public EdicionSubFamiliaEstadoUseCase(
            SubFamiliaService subFamiliaService
    ){
        this.subFamiliaService = subFamiliaService;
    }

    public ResponseEditarEstadoSubFamilia AnularSubFamilia(long idSubFamilia) {
        try {
            RequestEditarEstadoSubFamilia request = new RequestEditarEstadoSubFamilia();
            request.setIdSubFamilia(idSubFamilia);
            ResponseEditarEstadoSubFamilia response = subFamiliaService.EditarEstadoSubFamilia(request, 0);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoSubFamilia response = new ResponseEditarEstadoSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la sub familia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoSubFamilia response = new ResponseEditarEstadoSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoSubFamilia ActivarSubFamilia(long idSubFamilia) {
        try {
            RequestEditarEstadoSubFamilia request = new RequestEditarEstadoSubFamilia();
            request.setIdSubFamilia(idSubFamilia);
            ResponseEditarEstadoSubFamilia response = subFamiliaService.EditarEstadoSubFamilia(request, 1);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoSubFamilia response = new ResponseEditarEstadoSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al actualizar la sub familia: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoSubFamilia response = new ResponseEditarEstadoSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
