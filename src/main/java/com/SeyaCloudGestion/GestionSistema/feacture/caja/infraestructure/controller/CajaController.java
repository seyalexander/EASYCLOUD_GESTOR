package com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCajaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionCajaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/caja")
public class CajaController {

    private final ListaCajaUseCase listaCajaUseCase;
    private final RegistroCajaUseCase registroCajaUseCase;
    private final EdicionCajaUseCase edicionCajaUseCase;
    private final DetalleCajaUseCase detalleCajaUseCase;
    private final NotificacionCajaService notificacionCajaService;
    private final RegistroSerieCajaUseCase registroSerieCajaUseCase;

    public CajaController(
            ListaCajaUseCase listaCajaUseCase,
            RegistroCajaUseCase registroCajaUseCase,
            EdicionCajaUseCase edicionCajaUseCase,
            DetalleCajaUseCase detalleCajaUseCase,
            NotificacionCajaService notificacionCajaService, RegistroSerieCajaUseCase registroSerieCajaUseCase
    ) {
        this.listaCajaUseCase = listaCajaUseCase;
        this.registroCajaUseCase = registroCajaUseCase;
        this.edicionCajaUseCase = edicionCajaUseCase;
        this.detalleCajaUseCase = detalleCajaUseCase;
        this.notificacionCajaService = notificacionCajaService;
        this.registroSerieCajaUseCase = registroSerieCajaUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar cajas", description = "Obtiene la lista de cajas")
    public ResponseEntity<ResponseListaCaja> listaCaja() {
        ResponseListaCaja response = listaCajaUseCase.ListaCaja();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar caja", description = "Permite registrar una nueva caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caja registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroCaja> registroCaja(
            @Validated @RequestBody RequestRegistroCaja request) {

        ResponseRegistroCaja response = registroCajaUseCase.RegistroCaja(request);

        if (response.isExito()) {
            NotificacionCajaDTO notificacion = new NotificacionCajaDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nueva caja registrada");

            notificacionCajaService.enviarNotificacionCaja_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/serie")
    @Operation(summary = "Registrar serie caja", description = "Permite Asignar una serie a una  caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serie registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroSerieCaja> registroSerieCaja(
            @Validated @RequestBody RequestRegistroSerieCaja request) {

        ResponseRegistroSerieCaja response = registroSerieCajaUseCase.RegistroSerieCaja(request);

        if (response.isExito()) {
            NotificacionCajaDTO notificacion = new NotificacionCajaDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nueva Serie Caja registrada");

            notificacionCajaService.enviarNotificacionCaja_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar caja", description = "Permite editar todos los datos de una caja existente")
    public ResponseEntity<ResponseEditarAllCaja> edicionAllCaja(
            @Validated @RequestBody RequestEditarAllCaja request) {

        ResponseEditarAllCaja response = edicionCajaUseCase.EdicionAllCaja(request);

        if (response.isExito()) {
            NotificacionCajaDTO notificacion = new NotificacionCajaDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Caja editada");

            notificacionCajaService.enviarNotificacionCaja_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idCaja}")
    @Operation(summary = "Detalle caja", description = "Obtiene el detalle de una caja específica")
    public ResponseEntity<ResponseDetalleCaja> detalleCaja(@PathVariable long idCaja) {

        ResponseDetalleCaja response = detalleCajaUseCase.DetalleCaja(idCaja);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}