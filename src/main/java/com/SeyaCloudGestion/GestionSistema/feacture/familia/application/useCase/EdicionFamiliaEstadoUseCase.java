package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarEstadoFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarEstadoFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services.FamiliaService;
import org.springframework.stereotype.Component;

@Component
public class EdicionFamiliaEstadoUseCase {

    private final FamiliaService familiaService;

    public EdicionFamiliaEstadoUseCase(
            FamiliaService familiaService
    ){
        this.familiaService = familiaService;
    }

    public ResponseEditarEstadoFamilia EdicionAnularFamilia(long idFamilia) {
        try {
            RequestEditarEstadoFamilia request = new RequestEditarEstadoFamilia();
            request.setIdFamilia(idFamilia);
            ResponseEditarEstadoFamilia response = familiaService.EditarEstadoFamilia(request, 0);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoFamilia response = new ResponseEditarEstadoFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoFamilia response = new ResponseEditarEstadoFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoFamilia EdicionActivarFamilia(long idFamilia) {
        try {
            RequestEditarEstadoFamilia request = new  RequestEditarEstadoFamilia();
            request.setIdFamilia(idFamilia);
            ResponseEditarEstadoFamilia response = familiaService.EditarEstadoFamilia(request, 1);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoFamilia response = new ResponseEditarEstadoFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoFamilia response = new ResponseEditarEstadoFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
