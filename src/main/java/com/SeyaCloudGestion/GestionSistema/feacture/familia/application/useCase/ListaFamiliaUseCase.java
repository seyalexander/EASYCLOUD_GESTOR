package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestListaFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseListaFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.services.FamiliaService;
import org.springframework.stereotype.Component;

@Component
public class ListaFamiliaUseCase {
    private final FamiliaService familiaService;

    public ListaFamiliaUseCase(
            FamiliaService familiaService
    ){
        this.familiaService = familiaService;
    }

    public ResponseListaFamilia ListaFamilia(RequestListaFamilia request) {
        try {
            ResponseListaFamilia response = familiaService.listaFamilia(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaFamilia response = new ResponseListaFamilia();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setFamilia(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las familias: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaFamilia response = new ResponseListaFamilia();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setFamilia(java.util.List.of());
            return response;
        }
    }
}
