package com.SeyaCloudGestion.GestionSistema.feacture.rol.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.services.RolService;
import org.springframework.stereotype.Component;

@Component
public class EdicionRolesUseCase {
    private final RolService rolService;

    public EdicionRolesUseCase(
            RolService rolService
    ){
        this.rolService = rolService;
    }

    public ResponseEditarAllRol EdicionRoles(RequestEditarAllRol request) {
        try {
            ResponseEditarAllRol response = rolService.EditarRol(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllRol response = new ResponseEditarAllRol();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar el rol: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllRol response = new ResponseEditarAllRol();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
