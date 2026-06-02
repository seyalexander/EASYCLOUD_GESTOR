package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestEditarAllTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestListaTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestRegistroTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.*;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionTurnoCajaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/TurnoCaja")
public class TurnoCajaController {
    private final ListaTurnoCajaUseCase listaTurnoCajaUseCase;
    private final RegistroTurnoCajaUseCase registroTurnoCajaUseCase;
    private final EdicionTurnoCajaUseCase edicionTurnoCajaUseCase;
    private final EdicionTurnoCajaEstadoUseCase edicionTurnoCajaEstadoUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;
    private final NotificacionTurnoCajaService notificacionTurnoCajaService;

    public TurnoCajaController(ListaTurnoCajaUseCase listaTurnoCajaUseCase, RegistroTurnoCajaUseCase registroTurnoCajaUseCase, EdicionTurnoCajaUseCase edicionTurnoCajaUseCase, EdicionTurnoCajaEstadoUseCase edicionTurnoCajaEstadoUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase, NotificacionTurnoCajaService notificacionTurnoCajaService) {
        this.listaTurnoCajaUseCase = listaTurnoCajaUseCase;
        this.registroTurnoCajaUseCase = registroTurnoCajaUseCase;
        this.edicionTurnoCajaUseCase = edicionTurnoCajaUseCase;
        this.edicionTurnoCajaEstadoUseCase = edicionTurnoCajaEstadoUseCase;
        this.detalleTurnoCajaUseCase = detalleTurnoCajaUseCase;
        this.notificacionTurnoCajaService = notificacionTurnoCajaService;
    }

    @GetMapping
    @Operation(summary = "Listar turnos de caja", description = "Obtiene la lista de turnos de caja según los filtros enviados")
    public ResponseEntity<ResponseListaTurnoCaja> listaTurnoCaja(
            @Validated @ModelAttribute RequestListaTurnoCaja request
    ) {
        ResponseListaTurnoCaja response = listaTurnoCajaUseCase.ListaTurnoCaja(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar turno de caja", description = "Permite registrar un nuevo turno de caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno de caja registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroTurnoCaja> registroTurnoCaja(
            @Validated @RequestBody RequestRegistroTurnoCaja request
    ) {
        ResponseRegistroTurnoCaja response = registroTurnoCajaUseCase.RegistroTurnoCaja(request);

        if (response.isExito()) {
            NotificacionTurnoCajaDTO notificacion = new NotificacionTurnoCajaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo turno de caja registrado");

            notificacionTurnoCajaService.enviarNotificacionTurnoCaja_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar turno de caja", description = "Permite editar todos los datos de un turno de caja existente")
    public ResponseEntity<ResponseEditarAllTurnoCaja> edicionAllTurnoCaja(
            @Validated @RequestBody RequestEditarAllTurnoCaja request
    ) {
        ResponseEditarAllTurnoCaja response = edicionTurnoCajaUseCase.EdicionAllTurnoCaja(request);

        if (response.isExito()) {
            NotificacionTurnoCajaDTO notificacion = new NotificacionTurnoCajaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Turno de caja editado");

            notificacionTurnoCajaService.enviarNotificacionTurnoCaja_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idTurnoCaja}")
    @Operation(summary = "Detalle turno de caja", description = "Obtiene el detalle de un turno de caja")
    public ResponseEntity<ResponseDetalleTurnoCaja> detalleTurnoCaja(
            @PathVariable long idTurnoCaja
    ) {
        ResponseDetalleTurnoCaja response = detalleTurnoCajaUseCase.DetalleTurnoCaja(idTurnoCaja);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
