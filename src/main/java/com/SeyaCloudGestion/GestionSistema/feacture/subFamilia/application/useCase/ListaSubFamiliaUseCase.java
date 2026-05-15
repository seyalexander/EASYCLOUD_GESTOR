package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestListaSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseListaSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.services.SubFamiliaService;
import org.springframework.stereotype.Component;

@Component
public class ListaSubFamiliaUseCase {
    private final SubFamiliaService subFamiliaService;

    public ListaSubFamiliaUseCase(
            SubFamiliaService subFamiliaService
    ){
        this.subFamiliaService = subFamiliaService;
    }

    public ResponseListaSubFamilia ListaSubFamilia(RequestListaSubFamilia request) {
        try {
            ResponseListaSubFamilia response = subFamiliaService.ListaSubFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaSubFamilia response = new ResponseListaSubFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setSubfamilias(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las subfamilias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaSubFamilia response = new ResponseListaSubFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setSubfamilias(java.util.List.of());
            return response;
        }
    }
}
