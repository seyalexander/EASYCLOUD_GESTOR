package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestDetalleUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestEditarEstadoUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseDetalleUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseEditarEstadoUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.services.UsuarioService;
import org.springframework.stereotype.Component;

@Component
public class EdicionUsuarioEstadoUseCase {
    private final UsuarioService usuarioService;

    public EdicionUsuarioEstadoUseCase(
            UsuarioService usuarioService
    ){
        this.usuarioService = usuarioService;
    }

    public ResponseEditarEstadoUsuario AnularUsuario(long idUsuario) {
        try {
            RequestEditarEstadoUsuario request = new RequestEditarEstadoUsuario();
            request.setIdUsuario(idUsuario);
            ResponseEditarEstadoUsuario response = usuarioService.EditarEstadoUsuario(request, 0);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoUsuario response = new ResponseEditarEstadoUsuario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al anular al usuario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoUsuario response = new ResponseEditarEstadoUsuario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoUsuario ActivarUsuario(long idUsuario) {
        try {
            RequestEditarEstadoUsuario request = new RequestEditarEstadoUsuario();
            request.setIdUsuario(idUsuario);
            ResponseEditarEstadoUsuario response = usuarioService.EditarEstadoUsuario(request, 1);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseEditarEstadoUsuario response = new ResponseEditarEstadoUsuario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al activar al usuario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseEditarEstadoUsuario response = new ResponseEditarEstadoUsuario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
