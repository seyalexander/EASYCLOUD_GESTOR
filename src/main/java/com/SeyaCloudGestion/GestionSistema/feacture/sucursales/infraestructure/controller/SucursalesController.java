package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSucursalDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionSucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/Sucursales")
public class SucursalesController {
    private final ListaSucursalesUseCase listaSucursalesUseCase;
    private final RegistroSucursalesUseCase registroSucursalesUseCase;
    private final EdicionAllSucursalesUseCase edicionAllSucursalesUseCase;
    private final EdicionSucursalesEstadoUseCase edicionSucursalesUseCase;
    private final DetalleSucursalesUseCase detalleSucursalesUseCase;
    private final NotificacionSucursalService notificacionSucursalesService;

    public SucursalesController(
            ListaSucursalesUseCase listaSucursalesUseCase,
            RegistroSucursalesUseCase registroSucursalesUseCase,
            EdicionAllSucursalesUseCase edicionAllSucursalesUseCase, EdicionSucursalesEstadoUseCase edicionSucursalesUseCase,
            DetalleSucursalesUseCase detalleSucursalesUseCase, NotificacionSucursalService notificacionSucursalesService
    ) {
        this.listaSucursalesUseCase = listaSucursalesUseCase;
        this.registroSucursalesUseCase = registroSucursalesUseCase;
        this.edicionAllSucursalesUseCase = edicionAllSucursalesUseCase;
        this.edicionSucursalesUseCase = edicionSucursalesUseCase;
        this.detalleSucursalesUseCase = detalleSucursalesUseCase;
        this.notificacionSucursalesService = notificacionSucursalesService;
    }

    @GetMapping
    @Operation(summary = "Listar sucursales by estado", description = "Obtiene la lista de sucursales según su estado")
    public ResponseEntity<ResponseListaSucursales> listaSucursales(
            @Validated @ModelAttribute RequestListaSucursales request
    ) {
        ResponseListaSucursales response = listaSucursalesUseCase.ListaSucursales(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar sucursal", description = "Permite registrar una nueva sucursal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucursal registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroSucursales> registroSucursales(
            @Validated @RequestBody RequestRegistroSucursales request
    ) {
        ResponseRegistroSucursales response = registroSucursalesUseCase.RegistroSucursales(request);

        if (response.isExito()) {
            NotificacionSucursalDTO notificacion = new NotificacionSucursalDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nueva sucursal registrada");

            notificacionSucursalesService.enviarNotificacionSucursal_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar sucursal", description = "Permite editar todos los datos de una sucursal existente")
    public ResponseEntity<ResponseEditarAllSucursales> edicionAllSucursales(
            @Validated @RequestBody RequestEditarAllSucursales request
    ) {
        ResponseEditarAllSucursales response = edicionAllSucursalesUseCase.EdicionAllSucursales(request);

        if (response.isExito()) {
            NotificacionSucursalDTO notificacion = new NotificacionSucursalDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Sucursal editada");

            notificacionSucursalesService.enviarNotificacionSucursal_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idSucursales}")
    @Operation(summary = "Anular sucursal", description = "Cambia el estado de la sucursal a inactivo")
    public ResponseEntity<ResponseEditarEstadoSucursales> anularSucursales(@PathVariable long idSucursales) {
        ResponseEditarEstadoSucursales response = edicionSucursalesUseCase.AnularSucursales(idSucursales);

        if (response.isExito()) {
            NotificacionSucursalDTO notificacion = new NotificacionSucursalDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Surucrsal anulada");
            notificacion.setIdSucursales(idSucursales);

            notificacionSucursalesService.enviarNotificacionSucursal_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idSucursales}/activar")
    @Operation(summary = "Activar sucursal by id", description = "Activa nuevamente una sucursal previamente anulada")
    public ResponseEntity<ResponseEditarEstadoSucursales> activarSucursales(@PathVariable long idSucursales) {
        ResponseEditarEstadoSucursales response = edicionSucursalesUseCase.ActivarSucursales(idSucursales);

        if (response.isExito()) {
            NotificacionSucursalDTO notificacion = new NotificacionSucursalDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Surucrsal activada");
            notificacion.setIdSucursales(idSucursales);

            notificacionSucursalesService.enviarNotificacionSucursal_Activar(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idSucursales}")
    @Operation(summary = "Detalle sucursal", description = "Obtiene el detalle de una sucursal")
    public ResponseEntity<ResponseDetalleSucursales> detalleSucursales(@PathVariable long idSucursales) {
        ResponseDetalleSucursales response = detalleSucursalesUseCase.DetalleSucursales(idSucursales);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
