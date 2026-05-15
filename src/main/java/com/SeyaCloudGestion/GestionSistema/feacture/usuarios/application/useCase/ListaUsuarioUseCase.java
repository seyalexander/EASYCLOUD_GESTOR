package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestListaUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseListaUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.services.UsuarioService;
import org.springframework.stereotype.Component;

@Component
public class ListaUsuarioUseCase extends ResponseGeneral {
    private final UsuarioService usuarioService;

    public ListaUsuarioUseCase(
            UsuarioService usuarioService
    ){
        this.usuarioService = usuarioService;
    }

    public ResponseListaUsuario ListarUsuario(RequestListaUsuario request) {
        try {
            // VALIDACIÓN DE CAMPOS
            if (request == null) {
                String mensajeError = "El valor del estado es obligatorio";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getEstado() < 0 || request.getEstado() > 2 ) {
                String mensajeError = "El valor del estado no es el correcto";
                throw new IllegalArgumentException(mensajeError);
            }

            ResponseListaUsuario response = usuarioService.ListaUsuarios(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaUsuario response = new ResponseListaUsuario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setUsuarios(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar los usuarios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaUsuario response = new ResponseListaUsuario();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setUsuarios(java.util.List.of());
            return response;
        }
    }
}
