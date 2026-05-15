package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestDetalleUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseDetalleUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.services.UsuarioService;
import org.springframework.stereotype.Component;

@Component
public class DetalleUsuarioUseCase {
    private final UsuarioService usuarioService;

    public DetalleUsuarioUseCase(
            UsuarioService usuarioService
    ){
        this.usuarioService = usuarioService;
    }

    public ResponseDetalleUsuario DetalleUsuario(long idUsuario) {
        try {
            RequestDetalleUsuario request = new RequestDetalleUsuario();
            request.setIdUsuario(idUsuario);
            ResponseDetalleUsuario response = usuarioService.DetalleUsuario(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseDetalleUsuario response = new ResponseDetalleUsuario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setUsuario(null);
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al obtener el detalle del usuario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseDetalleUsuario response = new ResponseDetalleUsuario();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setUsuario(null);
            return response;
        }
    }
}
