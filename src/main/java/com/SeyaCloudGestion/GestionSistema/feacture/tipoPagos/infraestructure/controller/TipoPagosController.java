package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoPagoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionTipoPagoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarAllTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestListaTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestRegistroTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.useCase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/TipoPagos")
public class TipoPagosController {

    private final ListaTipoPagosUseCase listaTipoPagosUseCase;
    private final RegistroTipoPagosUseCase registroTipoPagosUseCase;
    private final EdicionAllTipoPagosUseCase edicionAllTipoPagosUseCase;
    private final EdicionTipoPagosEstadoUseCase edicionTipoPagosEstadoUseCase;
    private final DetalleTipoPagosUseCase detalleTipoPagosUseCase;
    private final NotificacionTipoPagoService notificacionTipoPagoService;

    public TipoPagosController(ListaTipoPagosUseCase listaTipoPagosUseCase, RegistroTipoPagosUseCase registroTipoPagosUseCase, EdicionAllTipoPagosUseCase edicionAllTipoPagosUseCase, EdicionTipoPagosEstadoUseCase edicionTipoPagosEstadoUseCase, DetalleTipoPagosUseCase detalleTipoPagosUseCase, NotificacionTipoPagoService notificacionTipoPagoService) {
        this.listaTipoPagosUseCase = listaTipoPagosUseCase;
        this.registroTipoPagosUseCase = registroTipoPagosUseCase;
        this.edicionAllTipoPagosUseCase = edicionAllTipoPagosUseCase;
        this.edicionTipoPagosEstadoUseCase = edicionTipoPagosEstadoUseCase;
        this.detalleTipoPagosUseCase = detalleTipoPagosUseCase;
        this.notificacionTipoPagoService = notificacionTipoPagoService;
    }

    @GetMapping
    @Operation(summary = "Listar tipos de pago", description = "Obtiene la lista de tipos de pago según los filtros enviados")
    public ResponseEntity<ResponseListaTipoPagos> listaTipoPagos(
            @Validated @ModelAttribute RequestListaTipoPagos request
    ) {
        ResponseListaTipoPagos response = listaTipoPagosUseCase.ListaTipoPagos(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar tipo de pago", description = "Permite registrar un nuevo tipo de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de pago registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroTipoPagos> registroTipoPagos(
            @Validated @RequestBody RequestRegistroTipoPagos request
    ) {
        ResponseRegistroTipoPagos response = registroTipoPagosUseCase.RegistroTipoPagos(request);

        if (response.isExito()) {
            NotificacionTipoPagoDTO notificacion = new NotificacionTipoPagoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo tipo de pago registrado");

            notificacionTipoPagoService.enviarNotificacionTipoPago_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar tipo de pago", description = "Permite editar todos los datos de un tipo de pago existente")
    public ResponseEntity<ResponseEditarAllTipoPagos> edicionAllTipoPagos(
            @Validated @RequestBody RequestEditarAllTipoPagos request
    ) {
        ResponseEditarAllTipoPagos response = edicionAllTipoPagosUseCase.EdicionAllTipoPagos(request);

        if (response.isExito()) {
            NotificacionTipoPagoDTO notificacion = new NotificacionTipoPagoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Tipo de pago editado");

            notificacionTipoPagoService.enviarNotificacionTipoPago_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idTipoPago}")
    @Operation(summary = "Anular tipo de pago", description = "Cambia el estado del tipo de pago a inactivo")
    public ResponseEntity<ResponseEditarEstadoTipoPagos> anularTipoPagos(
            @PathVariable long idTipoPago
    ) {
        ResponseEditarEstadoTipoPagos response = edicionTipoPagosEstadoUseCase.AnularTipoPagos(idTipoPago);

        if (response.isExito()) {
            NotificacionTipoPagoDTO notificacion = new NotificacionTipoPagoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Tipo de pago anulado");
            notificacion.setIdTipoPago(idTipoPago);

            notificacionTipoPagoService.enviarNotificacionTipoPago_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idTipoPago}/activar")
    @Operation(summary = "Activar tipo de pago", description = "Activa nuevamente un tipo de pago previamente anulado")
    public ResponseEntity<ResponseEditarEstadoTipoPagos> activarTipoPagos(
            @PathVariable long idTipoPago
    ) {
        ResponseEditarEstadoTipoPagos response = edicionTipoPagosEstadoUseCase.ActivarTipoPagos(idTipoPago);

        if (response.isExito()) {
            NotificacionTipoPagoDTO notificacion = new NotificacionTipoPagoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Tipo de pago activado");
            notificacion.setIdTipoPago(idTipoPago);

            notificacionTipoPagoService.enviarNotificacionTipoPago_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idTipoPago}")
    @Operation(summary = "Detalle tipo de pago", description = "Obtiene el detalle de un tipo de pago")
    public ResponseEntity<ResponseDetalleTipoPagos> detalleTipoPagos(
            @PathVariable long idTipoPago
    ) {
        ResponseDetalleTipoPagos response = detalleTipoPagosUseCase.DetalleTipoPagos(idTipoPago);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
