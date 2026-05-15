package com.SeyaCloudGestion.GestionSistema.feacture.rol.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestListaRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseListaRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.services.RolService;
import org.springframework.stereotype.Component;

@Component
public class ListaRolesUseCase {
    private final RolService rolService;

    public ListaRolesUseCase(
            RolService rolService
    ){
        this.rolService = rolService;
    }

    public ResponseListaRol ListaRoles(RequestListaRol request) {
        try {
            ResponseListaRol response = rolService.ListaRol(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaRol response = new ResponseListaRol();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setRoles(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar los roles: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaRol response = new ResponseListaRol();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setRoles(java.util.List.of());
            return response;
        }
    }
}
