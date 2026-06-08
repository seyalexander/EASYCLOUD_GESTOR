package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionTipoClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/TipoClientes")
public class TipoClientesController {

    private final DetalleTipoClientesUseCase detalleTipoClientesUseCase;
    private final EdicionTipoClientesUseCase edicionTipoClientesUseCase;
    private final EdicionTipoClientesEstadoUseCase edicionTipoClientesEstadoUseCase;
    private final ListaTipoClientesUseCase listaTipoClientesUseCase;
    private final RegistroTipoClientesUseCase registroTipoClientesUseCase;
    private final NotificacionTipoClienteService notificacionTipoClienteService;

    public TipoClientesController(DetalleTipoClientesUseCase detalleTipoClientesUseCase, EdicionTipoClientesUseCase edicionTipoClientesUseCase, EdicionTipoClientesEstadoUseCase edicionTipoClientesEstadoUseCase, ListaTipoClientesUseCase listaTipoClientesUseCase, RegistroTipoClientesUseCase registroTipoClientesUseCase, NotificacionTipoClienteService notificacionTipoClienteService) {
        this.detalleTipoClientesUseCase = detalleTipoClientesUseCase;
        this.edicionTipoClientesUseCase = edicionTipoClientesUseCase;
        this.edicionTipoClientesEstadoUseCase = edicionTipoClientesEstadoUseCase;
        this.listaTipoClientesUseCase = listaTipoClientesUseCase;
        this.registroTipoClientesUseCase = registroTipoClientesUseCase;
        this.notificacionTipoClienteService = notificacionTipoClienteService;
    }

    @GetMapping
    @Operation(summary = "Listar TipoClientes", description = "Obtiene la lista de TipoClientes según los filtros enviados")
    public ResponseEntity<ResponseListaTipoClientes> listaTipoClientes(@Validated @ModelAttribute RequestListaTipoClientes request) {
        ResponseListaTipoClientes response = listaTipoClientesUseCase.ListaTipoClientes(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar TipoClientes", description = "Permite registrar un nuevo registro de TipoClientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TipoClientes registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroTipoClientes> registroTipoClientes(@Validated @RequestBody RequestRegistroTipoClientes request) {
        ResponseRegistroTipoClientes response = registroTipoClientesUseCase.RegistroTipoClientes(request);

        if (response.isExito()) {
            NotificacionTipoClienteDTO notificacion = new NotificacionTipoClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo registro de TipoClientes registrado");
            notificacionTipoClienteService.enviarNotificacionTipoCliente_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar TipoClientes", description = "Permite editar todos los datos de TipoClientes")
    public ResponseEntity<ResponseEditarAllTipoClientes> edicionAllTipoClientes(@Validated @RequestBody RequestEditarAllTipoClientes request) {
        ResponseEditarAllTipoClientes response = edicionTipoClientesUseCase.EdicionAllTipoClientes(request);

        if (response.isExito()) {
            NotificacionTipoClienteDTO notificacion = new NotificacionTipoClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Registro de TipoClientes editado");
            notificacionTipoClienteService.enviarNotificacionTipoCliente_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idTipoClientes}")
    @Operation(summary = "Anular TipoClientes", description = "Cambia el estado de TipoClientes a inactivo")
    public ResponseEntity<ResponseEditarEstadoTipoClientes> anularTipoClientes(@PathVariable long idTipoClientes) {
        ResponseEditarEstadoTipoClientes response = edicionTipoClientesEstadoUseCase.AnularTipoCliente(idTipoClientes);

        if (response.isExito()) {
            NotificacionTipoClienteDTO notificacion = new NotificacionTipoClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Registro de TipoClientes anulado");
            notificacionTipoClienteService.enviarNotificacionTipoCliente_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idTipoClientes}/activar")
    @Operation(summary = "Activar TipoClientes", description = "Activa nuevamente un registro de TipoClientes previamente anulado")
    public ResponseEntity<ResponseEditarEstadoTipoClientes> activarTipoClientes(@PathVariable long idTipoClientes) {
        ResponseEditarEstadoTipoClientes response = edicionTipoClientesEstadoUseCase.ActivarTipoCliente(idTipoClientes);

        if (response.isExito()) {
            NotificacionTipoClienteDTO notificacion = new NotificacionTipoClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Registro de TipoClientes activado");
            notificacionTipoClienteService.enviarNotificacionTipoCliente_Activar(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idTipoClientes}")
    @Operation(summary = "Detalle TipoClientes", description = "Obtiene el detalle de TipoClientes")
    public ResponseEntity<ResponseDetalleTipoClientes> detalleTipoClientes(@PathVariable long idTipoClientes) {
        ResponseDetalleTipoClientes response = detalleTipoClientesUseCase.DetalleTipoClientes(idTipoClientes);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
