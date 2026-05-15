package com.SeyaCloudGestion.GestionSistema.feacture.rol.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.ResponseRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.domain.services.RolService;
import org.springframework.stereotype.Component;

@Component
public class RegistroRolesUseCase {
    private final RolService rolService;

    public RegistroRolesUseCase(
            RolService rolService
    ){
        this.rolService = rolService;
    }

    public ResponseRegistroRol RegistroRoles(RequestRegistroRol request) {
        try {
            // VALIDACIÓN DE CAMPOS
            if (request == null) {
                String mensajeError = "No se encontró datos para registrar";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getDescripcion() == null || request.getDescripcion().isEmpty()) {
                String mensajeError = "La descripción del rol no puede estar vacío";
                throw new IllegalArgumentException(mensajeError);
            }

            ResponseRegistroRol response = rolService.registrarRol(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroRol response = new ResponseRegistroRol();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar el rol: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroRol response = new ResponseRegistroRol();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
