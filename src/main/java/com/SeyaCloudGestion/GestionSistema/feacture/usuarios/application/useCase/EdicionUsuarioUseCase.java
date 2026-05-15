package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestEditarAllUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestEditarEstadoUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseEditarAllUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseEditarEstadoUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.services.UsuarioService;
import org.springframework.stereotype.Component;

@Component
public class EdicionUsuarioUseCase {
    private final UsuarioService usuarioService;

    public EdicionUsuarioUseCase(
            UsuarioService usuarioService
    ){
        this.usuarioService = usuarioService;
    }

    public ResponseEditarAllUsuario EditarUsuario(RequestEditarAllUsuario request) {
        try {
            ResponseEditarAllUsuario response = usuarioService.EditarUsuario(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarAllUsuario response = new ResponseEditarAllUsuario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al editar al usuario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarAllUsuario response = new ResponseEditarAllUsuario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
