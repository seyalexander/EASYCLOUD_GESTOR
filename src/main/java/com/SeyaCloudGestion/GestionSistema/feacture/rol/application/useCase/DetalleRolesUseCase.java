package com.SeyaCloudGestion.GestionSistema.feacture.rol.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestDetalleRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseDetalleRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.services.RolService;
import org.springframework.stereotype.Component;

@Component
public class DetalleRolesUseCase {
    private final RolService rolService;

    public DetalleRolesUseCase(
            RolService rolService
    ){
        this.rolService = rolService;
    }

    public ResponseDetalleRol DetalleRoles(long idRol) {
        try {
            RequestDetalleRol request = new RequestDetalleRol();
            request.setIdRol(idRol);
            ResponseDetalleRol response = rolService.DetalleRol(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleRol response = new ResponseDetalleRol();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setRol(null);
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado ver el detalle del rol: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleRol response = new ResponseDetalleRol();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setRol(null);
            return response;
        }
    }
}
