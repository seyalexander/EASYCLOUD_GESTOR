package com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {
    private final DetalleClienteUseCase detalleClienteUseCase;
    private final EdicionClienteEstadoUseCase edicionClienteEstadoUseCase;
    private final ListaClienteUseCase listaClienteUseCase;
    private final EdicionClienteAllUseCase edicionClienteAllUseCase;
    private final RegistroClienteUseCase    registroClienteUseCase;
    private final NotificacionClienteService notificacionClienteService;

    public ClienteController(DetalleClienteUseCase detalleClienteUseCase, EdicionClienteEstadoUseCase edicionClienteEstadoUseCase, EdicionClienteAllUseCase edicionClienteUseCase, EdicionClienteEstadoUseCase edicionClienteUseCase1, ListaClienteUseCase listaClienteUseCase, EdicionClienteAllUseCase edicionClienteAllUseCase, RegistroClienteUseCase registroClienteUseCase, NotificacionClienteService notificacionClienteService) {
        this.detalleClienteUseCase = detalleClienteUseCase;
        this.edicionClienteEstadoUseCase = edicionClienteEstadoUseCase;
        this.listaClienteUseCase = listaClienteUseCase;
        this.edicionClienteAllUseCase = edicionClienteAllUseCase;
        this.registroClienteUseCase = registroClienteUseCase;
        this.notificacionClienteService = notificacionClienteService;
    }

    @GetMapping
    @Operation(summary = "Listar clientes by estado", description = "Obtiene la lista de clientes según su estado")
    public ResponseEntity<ResponseListaCliente> listaCliente(@Validated @ModelAttribute RequestListaCliente request) {
        ResponseListaCliente response = listaClienteUseCase.ListaCliente(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "Registrar cliente",
            description = "Permite registrar un nuevo cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroCliente> registroCliente(
            @Validated @RequestBody RequestRegistroCliente request
    ) {
        ResponseRegistroCliente response = registroClienteUseCase.RegistroCliente(request);

        if (response.isExito()) {
            NotificacionClienteDTO notificacion = new NotificacionClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo cliente registrado");

            notificacionClienteService.enviarNotificacionCliente_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(
            summary = "Editar cliente",
            description = "Permite editar todos los datos de un cliente existente"
    )
    public ResponseEntity<ResponseEditarAllCliente> edicionAllCliente(@Validated @RequestBody RequestEditarAllCliente request) {
        ResponseEditarAllCliente response = edicionClienteAllUseCase.EdicionAllCliente(request);

        if (response.isExito()) {
            NotificacionClienteDTO notificacion = new NotificacionClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Cliente editado");

            notificacionClienteService.enviarNotificacionCliente_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idCliente}")
    @Operation(summary = "Anular cliente", description = "Cambia el estado del cliente a inactivo")
    public ResponseEntity<ResponseEditarEstadoCliente> anularCliente(@PathVariable long idCliente) {
        ResponseEditarEstadoCliente response = edicionClienteEstadoUseCase.AnularCliente(idCliente);

        if (response.isExito()) {
            NotificacionClienteDTO notificacion = new NotificacionClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Cliente anulado");
            notificacion.setIdCliente(idCliente);

            notificacionClienteService.enviarNotificacionCliente_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idCliente}/activar")
    @Operation(
            summary = "Activar cliente by id",
            description = "Activa nuevamente un cliente previamente anulado"
    )
    public ResponseEntity<ResponseEditarEstadoCliente> activarCliente(@PathVariable long idCliente) {
        ResponseEditarEstadoCliente response = edicionClienteEstadoUseCase.ActivarCliente(idCliente);

        if (response.isExito()) {
            NotificacionClienteDTO notificacion = new NotificacionClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Cliente activado");
            notificacion.setIdCliente(idCliente);

            notificacionClienteService.enviarNotificacionCliente_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idCliente}")
    @Operation(summary = "Detalle cliente", description = "Obtiene el detalle de un cliente")
    public ResponseEntity<ResponseDetalleCliente> detalleCliente(
            @PathVariable long idCliente
    ) {
        ResponseDetalleCliente response = detalleClienteUseCase.DetalleCliente(idCliente);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
