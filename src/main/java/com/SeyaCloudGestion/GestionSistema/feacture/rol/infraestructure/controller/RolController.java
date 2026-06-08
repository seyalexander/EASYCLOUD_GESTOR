package com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestEditarAllRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestListaRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request.RequestRegistroRol;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.rol.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionRolDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionRolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rol")
public class RolController {

    @Autowired
    private ListaRolesUseCase listaRolesUseCase;
    @Autowired
    private RegistroRolesUseCase registroRolesUseCase;
    @Autowired
    private EdicionRolesUseCase edicionRolesUseCase;
    @Autowired
    private EdicionRolesEstadoUseCase edicionRolesEstadoUseCase;
    @Autowired
    private DetalleRolesUseCase detalleRolesUseCase;
    @Autowired
    private NotificacionRolService notificacionRolService;

    @GetMapping
    @Operation(summary = "Listar roles by estado", description = "Obtiene la lista de roles según su estado")
    public ResponseEntity<ResponseListaRol> listaFamilia(@Validated @ModelAttribute RequestListaRol request) {

        ResponseListaRol response = listaRolesUseCase.ListaRoles(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar rol", description = "Permite registrar un nuevo rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroRol> registroRol(
            @Validated @RequestBody RequestRegistroRol request) {

        ResponseRegistroRol response = registroRolesUseCase.RegistroRoles(request);

        if (response.isExito()) {
            NotificacionRolDTO notificacion = new NotificacionRolDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nuevo rol registrado");

            notificacionRolService.enviarNotificacionRol_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar Rol", description = "Permite editar todos los datos de un rol existente")
    public ResponseEntity<ResponseEditarAllRol> edicionAllRol(
            @Validated @RequestBody RequestEditarAllRol request) {

        ResponseEditarAllRol response = edicionRolesUseCase.EdicionRoles(request);

        if (response.isExito()) {
            NotificacionRolDTO notificacion = new NotificacionRolDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Rol editado");

            notificacionRolService.enviarNotificacionRol_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idRol}")
    @Operation(summary = "Anular rol", description = "Cambia el estado del rol a inactivo")
    public ResponseEntity<ResponseEditarEstadoRol> anularRol(@PathVariable long idRol) {

        ResponseEditarEstadoRol response = edicionRolesEstadoUseCase.AnularRol(idRol);

        if (response.isExito()) {
            NotificacionRolDTO notificacion = new NotificacionRolDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Rol anulado");
            notificacion.setIdRol(idRol);

            notificacionRolService.enviarNotificacionRol_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idRol}/activar")
    @Operation(summary = "Activar rol by id", description = "Activa nuevamente un rol previamente anulada")
    public ResponseEntity<ResponseEditarEstadoRol> activarRol(@PathVariable long idRol) {

        ResponseEditarEstadoRol response = edicionRolesEstadoUseCase.ActivarRol(idRol);

        if (response.isExito()) {
            NotificacionRolDTO notificacion = new NotificacionRolDTO();
            notificacion.setTipo("ACTIVACION");
            notificacion.setMensaje("Rol activado");
            notificacion.setIdRol(idRol);

            notificacionRolService.enviarNotificacionRol_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idRol}")
    @Operation(summary = "Detalle Rol", description = "Obtiene el detalle de un rol")
    public ResponseEntity<ResponseDetalleRol> detalleRol(@PathVariable long idRol) {

        ResponseDetalleRol response = detalleRolesUseCase.DetalleRoles(idRol);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
