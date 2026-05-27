package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionImpuestoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionImpuestoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/impuesto")
public class ImpuestoController {
    private final RegistroImpuestoUseCase registroImpuestoUseCase;
    private final ListaImpuestoUseCase listaImpuestoUseCase;
    private final DetalleImpuestoUseCase detalleImpuestoUseCase;
    private final EdicionImpuestoAllUseCase edicionImpuestoAllUseCase;
    private final EdicionImpuestoEstadoUseCase edicionImpuestoEstadoUseCase;
    private final NotificacionImpuestoService notificacionImpuestoService;

    public ImpuestoController(RegistroImpuestoUseCase registroImpuestoUseCase, ListaImpuestoUseCase listaImpuestoUseCase, DetalleImpuestoUseCase detalleImpuestoUseCase, EdicionImpuestoAllUseCase edicionImpuestoAllUseCase, EdicionImpuestoEstadoUseCase edicionImpuestoEstadoUseCase, NotificacionImpuestoService notificacionImpuestoService) {
        this.registroImpuestoUseCase = registroImpuestoUseCase;
        this.listaImpuestoUseCase = listaImpuestoUseCase;
        this.detalleImpuestoUseCase = detalleImpuestoUseCase;
        this.edicionImpuestoAllUseCase = edicionImpuestoAllUseCase;
        this.edicionImpuestoEstadoUseCase = edicionImpuestoEstadoUseCase;
        this.notificacionImpuestoService = notificacionImpuestoService;
    }

    @GetMapping
    @Operation(summary = "Listar impuestos by estado", description = "Obtiene la lista de impuestos según su estado")
    public ResponseEntity<ResponseListaImpuesto> listaImpuesto(@Validated @ModelAttribute RequestListaImpuesto request) {
        ResponseListaImpuesto response = listaImpuestoUseCase.ListaImpuesto(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar impuesto", description = "Permite registrar un nuevo impuesto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Impuesto registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroImpuesto> registroImpuesto(
            @Validated @RequestBody RequestRegistroImpuesto request
    ) {
        ResponseRegistroImpuesto response = registroImpuestoUseCase.RegistroImpuesto(request);

        if (response.isExito()) {
            NotificacionImpuestoDTO notificacion = new NotificacionImpuestoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo impuesto registrado");

            notificacionImpuestoService.enviarNotificacionImpuesto_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar impuesto", description = "Permite editar todos los datos de un impuesto existente")
    public ResponseEntity<ResponseEditarAllImpuesto> edicionAllImpuesto(
            @Validated @RequestBody RequestEditarAllImpuesto request
    ) {
        ResponseEditarAllImpuesto response = edicionImpuestoAllUseCase.EditarAllImpuesto(request);

        if (response.isExito()) {
            NotificacionImpuestoDTO notificacion = new NotificacionImpuestoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Impuesto editado");

            notificacionImpuestoService.enviarNotificacionImpuesto_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idImpuesto}")
    @Operation(summary = "Anular impuesto", description = "Cambia el estado del impuesto a inactivo")
    public ResponseEntity<ResponseEditarEstadoImpuesto> anularImpuesto(@PathVariable long idImpuesto) {
        ResponseEditarEstadoImpuesto response = edicionImpuestoEstadoUseCase.AnularImpuesto(idImpuesto);

        if (response.isExito()) {
            NotificacionImpuestoDTO notificacion = new NotificacionImpuestoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Impuesto anulado");
            notificacion.setIdImpuesto(idImpuesto);

            notificacionImpuestoService.enviarNotificacionImpuesto_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idImpuesto}/activar")
    @Operation(summary = "Activar impuesto by id", description = "Activa nuevamente un impuesto previamente anulado")
    public ResponseEntity<ResponseEditarEstadoImpuesto> activarImpuesto(@PathVariable long idImpuesto) {
        ResponseEditarEstadoImpuesto response = edicionImpuestoEstadoUseCase.ActivarImpuesto(idImpuesto);

        if (response.isExito()) {
            NotificacionImpuestoDTO notificacion = new NotificacionImpuestoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Impuesto activado");
            notificacion.setIdImpuesto(idImpuesto);

            notificacionImpuestoService.enviarNotificacionImpuesto_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idImpuesto}")
    @Operation(summary = "Detalle impuesto", description = "Obtiene el detalle de un impuesto")
    public ResponseEntity<ResponseDetalleImpuesto> detalleImpuesto(@PathVariable long idImpuesto) {
        ResponseDetalleImpuesto response = detalleImpuestoUseCase.DetalleImpuesto(idImpuesto);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
