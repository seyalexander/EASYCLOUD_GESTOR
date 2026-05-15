package com.SeyaCloudGestion.GestionSistema.feacture.rol.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarEstadoRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseEditarEstadoRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.services.RolService;
import org.springframework.stereotype.Component;

@Component
public class EdicionRolesEstadoUseCase {
    private final RolService rolService;

    public EdicionRolesEstadoUseCase(
            RolService rolService
    ){
        this.rolService = rolService;
    }

    public ResponseEditarEstadoRol AnularRol(long idRol) {
        try {
            RequestEditarEstadoRol request =  new RequestEditarEstadoRol();
            request.setIdRol(idRol);
            ResponseEditarEstadoRol response = rolService.EditarEstadoRol(request, 0);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoRol response = new ResponseEditarEstadoRol();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al anular el rol: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoRol response = new ResponseEditarEstadoRol();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoRol ActivarRol(long idRol) {
        try {
            RequestEditarEstadoRol request =  new RequestEditarEstadoRol();
            request.setIdRol(idRol);
            ResponseEditarEstadoRol response = rolService.EditarEstadoRol(request, 1);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoRol response = new ResponseEditarEstadoRol();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al activar el rol: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoRol response = new ResponseEditarEstadoRol();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
