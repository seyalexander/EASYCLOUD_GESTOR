package com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestEditarAllMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestListaMonedas;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestRegistroMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMonedaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionMonedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/moneda")
public class MonedaController {

    @Autowired
    private ListarMonedaUseCase listarMonedaUseCase;
    @Autowired
    private RegistroMonedaUseCase registroMonedaUseCase;
    @Autowired
    private EditarAllMonedaUseCase editarAllMonedaUseCase;
    @Autowired
    private EditarEstadoMonedaUseCase editarEstadoMonedaUseCase;
    @Autowired
    private DetalleMonedaUseCase detalleMonedaUseCase;
    @Autowired
    private EditarPredeterminadoMonedaUseCase editarPredeterminadoMonedaUseCase;
    @Autowired
    private NotificacionMonedaService notificacionMonedaService;


    @GetMapping
    @Operation(summary = "Listar monedas by estado", description = "Obtiene la lista de monedas según su estado")
    public ResponseEntity<ResponseListaMoneda> listaMoneda(@Validated @ModelAttribute RequestListaMonedas request) {
        ResponseListaMoneda response = listarMonedaUseCase.ListaMoneda(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar moneda", description = "Permite registrar una nueva moneda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Moneda registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroMoneda> registroEmpresa(
            @Validated @RequestBody RequestRegistroMoneda request) {

        ResponseRegistroMoneda response = registroMonedaUseCase.RegistrarMoneda(request);

        if (response.isExito()) {
            NotificacionMonedaDTO notificacion = new NotificacionMonedaDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nueva moneda registrada");

            notificacionMonedaService.enviarNotificacionMoneda_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar moneda", description = "Permite editar todos los datos de una moneda existente")
    public ResponseEntity<ResponseEditarAllMoneda> edicionAllEmpresa(
            @Validated @RequestBody RequestEditarAllMoneda request) {

        ResponseEditarAllMoneda response = editarAllMonedaUseCase.EditarAllMoneda(request);

        if (response.isExito()) {
            NotificacionMonedaDTO notificacion = new NotificacionMonedaDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Moneda editada");

            notificacionMonedaService.enviarNotificacionMoneda_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idMoneda}")
    @Operation(summary = "Anular moneda", description = "Cambia el estado de una moneda a inactivo")
    public ResponseEntity<ResponseEditarEstadoMoneda> anularMoneda(@PathVariable long idMoneda) {

        ResponseEditarEstadoMoneda response = editarEstadoMonedaUseCase.AnularAllMoneda(idMoneda);
        log.info("Response desde controlador: " + response);
        if (response.isExito()) {
            NotificacionMonedaDTO notificacion = new NotificacionMonedaDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Moneda anulada");
            notificacion.setIdMoneda(idMoneda);

            notificacionMonedaService.enviarNotificacionMoneda_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idMoneda}/activar")
    @Operation(summary = "Activar moneda by id", description = "Activa nuevamente una moneda previamente anulado")
    public ResponseEntity<ResponseEditarEstadoMoneda> activarMoneda(@PathVariable long idMoneda) {

        ResponseEditarEstadoMoneda response = editarEstadoMonedaUseCase.ActivarAllMoneda(idMoneda);

        if (response.isExito()) {
            NotificacionMonedaDTO notificacion = new NotificacionMonedaDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Moneda anulada");
            notificacion.setIdMoneda(idMoneda);

            notificacionMonedaService.enviarNotificacionMoneda_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idMoneda}/predeterminado")
    @Operation(summary = "Activar moneda by id", description = "Configurar la moneda a predeterminada")
    public ResponseEntity<ResponseEditarPredeterminadoMoneda> predeterminadoMoneda(@PathVariable long idMoneda) {

        ResponseEditarPredeterminadoMoneda response = editarPredeterminadoMonedaUseCase.EditarPredeterminadoMoneda(idMoneda);

        if (response.isExito()) {
            NotificacionMonedaDTO notificacion = new NotificacionMonedaDTO();
            notificacion.setTipo("PREDETERMINADO");
            notificacion.setMensaje("Moneda actualizada");
            notificacion.setIdMoneda(idMoneda);

            notificacionMonedaService.enviarNotificacionMoneda_Predeterminada(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idMoneda}")
    @Operation(summary = "Detalle moneda", description = "Obtiene el detalle de una moneda")
    public ResponseEntity<ResponseDetalleMoneda> detalleMoneda(@PathVariable long idMoneda) {

        ResponseDetalleMoneda response = detalleMonedaUseCase.DetalleMoneda(idMoneda);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
