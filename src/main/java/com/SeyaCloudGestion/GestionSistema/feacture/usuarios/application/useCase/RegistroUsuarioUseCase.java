package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestRegistroUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.ResponseRegistroUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.domain.services.UsuarioService;
import com.SeyaCloudGestion.GestionSistema.security.PasswordSecurityService;
import org.springframework.stereotype.Component;

@Component
public class RegistroUsuarioUseCase {
    private final UsuarioService usuarioService;
    private final PasswordSecurityService passwordSecurityService;

    public RegistroUsuarioUseCase(
            UsuarioService usuarioService,
            PasswordSecurityService passwordSecurityService
    ){
        this.usuarioService = usuarioService;
        this.passwordSecurityService = passwordSecurityService;
    }

    public ResponseRegistroUsuario RegistroUsuario(RequestRegistroUsuario request) {
        try {

            // VALIDACIÓN DE CAMPOS
            if (request == null) {
                String mensajeError = "No se encontró datos para registrar";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getPassowrd() == null || request.getPassowrd().isEmpty()) {
                String mensajeError = "La contraseña no puede estar vacía";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getUsuario() == null || request.getUsuario().isEmpty()) {
                String mensajeError = "El usuario no puede estar vacío";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getIdRol() == 0) {
                String mensajeError = "Le debe asignar un rol al usuario";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getIdEmpleado() == 0) {
                String mensajeError = "Le debe asignar un empleado al usuario";
                throw new IllegalArgumentException(mensajeError);
            }

            // ENCRIPTACION DE CONTRASEÑA
            String passwordEncriptada = passwordSecurityService.encriptarPassword(request.getPassowrd());
            request.setPassowrd(passwordEncriptada);

            // Consumo de store
            ResponseRegistroUsuario response = usuarioService.registrarUsuario(request);
            if(response.isExito()){}

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroUsuario response = new ResponseRegistroUsuario();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar al usuario: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroUsuario response = new ResponseRegistroUsuario();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
