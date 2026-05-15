package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestListaRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestEditarAllUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestListaUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request.RequestRegistroUsuario;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionRolDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUsuarioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {

    @Autowired
    private ListaUsuarioUseCase listaUsuarioUseCase;
    @Autowired
    private RegistroUsuarioUseCase registroUsuarioUseCase;
    @Autowired
    private DetalleUsuarioUseCase detalleUsuarioUseCase;
    @Autowired
    private EdicionUsuarioEstadoUseCase edicionUsuarioEstadoUseCase;
    @Autowired
    private EdicionUsuarioUseCase edicionUsuarioUseCase;
    @Autowired
    private NotificacionUsuarioService notificacionUsuarioService;

    @GetMapping
    @Operation(summary = "Listar usuarios by estado", description = "Obtiene la lista de usuarios según su estado")
    public ResponseEntity<ResponseListaUsuario> listaFamilia(@Validated @ModelAttribute RequestListaUsuario request) {

        ResponseListaUsuario response = listaUsuarioUseCase.ListarUsuario(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar usuario", description = "Permite registrar un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroUsuario> registroUsuario(
            @Validated @RequestBody RequestRegistroUsuario request) {

        ResponseRegistroUsuario response = registroUsuarioUseCase.RegistroUsuario(request);

        if (response.isExito()) {
            NotificacionUsuarioDTO notificacion = new NotificacionUsuarioDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nuevo usuario registrado");

            notificacionUsuarioService.enviarNotificacionUsuario_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar Usuario", description = "Permite editar todos los datos de un usuario existente")
    public ResponseEntity<ResponseEditarAllUsuario> edicionAllUsuario(
            @Validated @RequestBody RequestEditarAllUsuario request) {

        ResponseEditarAllUsuario response = edicionUsuarioUseCase.EditarUsuario(request);

        if (response.isExito()) {
            NotificacionUsuarioDTO notificacion = new NotificacionUsuarioDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Usuario editado");

            notificacionUsuarioService.enviarNotificacionUsuario_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idUsuario}")
    @Operation(summary = "Anular usuario", description = "Cambia el estado del usuario a inactivo")
    public ResponseEntity<ResponseEditarEstadoUsuario> anularUsuario(@PathVariable long idUsuario) {

        ResponseEditarEstadoUsuario response = edicionUsuarioEstadoUseCase.AnularUsuario(idUsuario);

        if (response.isExito()) {
            NotificacionUsuarioDTO notificacion = new NotificacionUsuarioDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Usuario anulado");
            notificacion.setIdRol(idUsuario);

            notificacionUsuarioService.enviarNotificacionUsuario_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idUsuario}/activar")
    @Operation(summary = "Activar usuario by id", description = "Activa nuevamente un usuario previamente anulada")
    public ResponseEntity<ResponseEditarEstadoUsuario> activarRol(@PathVariable long idUsuario) {

        ResponseEditarEstadoUsuario response = edicionUsuarioEstadoUseCase.ActivarUsuario(idUsuario);

        if (response.isExito()) {
            NotificacionUsuarioDTO notificacion = new NotificacionUsuarioDTO();
            notificacion.setTipo("ACTIVACION");
            notificacion.setMensaje("Rol activado");
            notificacion.setIdRol(idUsuario);

            notificacionUsuarioService.enviarNotificacionUsuario_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idUsuario}")
    @Operation(summary = "Detalle Usuario", description = "Obtiene el detalle de un usuario")
    public ResponseEntity<ResponseDetalleUsuario> detalleUsuario(@PathVariable long idUsuario) {

        ResponseDetalleUsuario response = detalleUsuarioUseCase.DetalleUsuario(idUsuario);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
