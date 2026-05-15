package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestListaEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.ResponseListaEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.services.EmpresaService;
import org.springframework.stereotype.Component;

@Component
public class ListaEmpresaUseCase {
    private final EmpresaService empresaService;

    public ListaEmpresaUseCase(
            EmpresaService empresaService
    ){
        this.empresaService = empresaService;
    }

    public ResponseListaEmpresa ListarEmpresa(RequestListaEmpresa request) {
        try {

            ResponseListaEmpresa response = empresaService.listaEmpresa(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaEmpresa response = new ResponseListaEmpresa();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setEmpresas(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las empresas: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaEmpresa response = new ResponseListaEmpresa();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setEmpresas(java.util.List.of());
            return response;
        }
    }
}
