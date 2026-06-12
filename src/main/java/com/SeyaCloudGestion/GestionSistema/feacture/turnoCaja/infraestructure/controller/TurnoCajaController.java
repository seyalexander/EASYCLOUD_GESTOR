package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestCerrarTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestListaTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestAbrirTurnoCaja;
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
    private final AbrirTurnoCajaUseCase abrirTurnoCajaUseCase;
    private final CerrarTurnoCajaUseCase cerrarTurnoCajaUseCase;
    private final DetalleTurnoCajaUseCase detalleTurnoCajaUseCase;
    private final NotificacionTurnoCajaService notificacionTurnoCajaService;

    public TurnoCajaController(ListaTurnoCajaUseCase listaTurnoCajaUseCase, AbrirTurnoCajaUseCase abrirTurnoCajaUseCase, CerrarTurnoCajaUseCase cerrarTurnoCajaUseCase, DetalleTurnoCajaUseCase detalleTurnoCajaUseCase, NotificacionTurnoCajaService notificacionTurnoCajaService) {
        this.listaTurnoCajaUseCase = listaTurnoCajaUseCase;
        this.abrirTurnoCajaUseCase = abrirTurnoCajaUseCase;
        this.cerrarTurnoCajaUseCase = cerrarTurnoCajaUseCase;
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
    @Operation(summary = "Abrir turno de caja", description = "Permite Abrir un nuevo turno de caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno de caja registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseAbrirTurnoCaja> abrirTurnoCaja(
            @Validated @RequestBody RequestAbrirTurnoCaja request
    ) {
        ResponseAbrirTurnoCaja response = abrirTurnoCajaUseCase.RegistroTurnoCaja(request);

        if (response.isExito()) {
            NotificacionTurnoCajaDTO notificacion = new NotificacionTurnoCajaDTO();
            notificacion.setTipo("CAJA ABIERTA");
            notificacion.setMensaje("Caja ABIERTA");

            notificacionTurnoCajaService.enviarNotificacionTurnoCaja_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Cerrar turno de caja", description = "Permite Cerrar un turno de caja existente")
    public ResponseEntity<ResponseCerrarTurnoCaja> cerrarTurnoCaja(
            @Validated @RequestBody RequestCerrarTurnoCaja request
    ) {
        ResponseCerrarTurnoCaja response = cerrarTurnoCajaUseCase.CerrarTurnoCaja(request);

        if (response.isExito()) {
            NotificacionTurnoCajaDTO notificacion = new NotificacionTurnoCajaDTO();
            notificacion.setTipo("Cerrado");
            notificacion.setMensaje("Turno de caja cerrado");

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
