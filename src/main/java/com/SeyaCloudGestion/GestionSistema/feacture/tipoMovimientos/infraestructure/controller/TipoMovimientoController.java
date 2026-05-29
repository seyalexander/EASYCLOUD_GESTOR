package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.controller;
import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoMovimientoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionTipoMovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/tipoMovimiento")
public class TipoMovimientoController {

    private final ListaTipoMovimientoUseCase listaTipoMovimientoUseCase;
    private final RegistroTipoMovimientoUseCase registroTipoMovimientoUseCase;
    private final EdicionAllTipoMovimientoUseCase edicionAllTipoMovimientoUseCase;
    private final EdicionTipoMovimientoEstadoUseCase edicionTipoMovimientoUseCase;
    private final DetalleTipoMovimientoUseCase detalleTipoMovimientoUseCase;
    private final NotificacionTipoMovimientoService notificacionTipoMovimientoService;

    public TipoMovimientoController(
            ListaTipoMovimientoUseCase listaTipoMovimientoUseCase,
            RegistroTipoMovimientoUseCase registroTipoMovimientoUseCase,
            EdicionAllTipoMovimientoUseCase edicionAllTipoMovimientoUseCase, EdicionTipoMovimientoEstadoUseCase edicionTipoMovimientoUseCase,
            DetalleTipoMovimientoUseCase detalleTipoMovimientoUseCase,
            NotificacionTipoMovimientoService notificacionTipoMovimientoService
    ) {
        this.listaTipoMovimientoUseCase = listaTipoMovimientoUseCase;
        this.registroTipoMovimientoUseCase = registroTipoMovimientoUseCase;
        this.edicionAllTipoMovimientoUseCase = edicionAllTipoMovimientoUseCase;
        this.edicionTipoMovimientoUseCase = edicionTipoMovimientoUseCase;
        this.detalleTipoMovimientoUseCase = detalleTipoMovimientoUseCase;
        this.notificacionTipoMovimientoService = notificacionTipoMovimientoService;
    }

    @GetMapping
    @Operation(summary = "Listar tipos de movimiento by estado", description = "Obtiene la lista de tipos de movimiento según su estado")
    public ResponseEntity<ResponseListaTipoMovimiento> listaTipoMovimiento(@Validated @ModelAttribute RequestListaTipoMovimiento request) {
        ResponseListaTipoMovimiento response = listaTipoMovimientoUseCase.ListaTipoMovimiento(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar tipo de movimiento", description = "Permite registrar un nuevo tipo de movimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de movimiento registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroTipoMovimiento> registroTipoMovimiento(@Validated @RequestBody RequestRegistroTipoMovimiento request) {
        ResponseRegistroTipoMovimiento response = registroTipoMovimientoUseCase.RegistroTipoMovimiento(request);

        if (response.isExito()) {
            NotificacionTipoMovimientoDTO notificacion = new NotificacionTipoMovimientoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo tipo de movimiento registrado");

            notificacionTipoMovimientoService.enviarNotificacionTipoMovimiento_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar tipo de movimiento", description = "Permite editar todos los datos de un tipo de movimiento existente")
    public ResponseEntity<ResponseEditarAllTipoMovimiento> edicionAllTipoMovimiento(@Validated @RequestBody RequestEditarAllTipoMovimiento request) {
        ResponseEditarAllTipoMovimiento response = edicionAllTipoMovimientoUseCase.EdicionAllTipoMovimiento(request);

        if (response.isExito()) {
            NotificacionTipoMovimientoDTO notificacion = new NotificacionTipoMovimientoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Tipo de movimiento editado");
            notificacion.setIdTipoMovimiento(request.getIdTipoMovimiento());

            notificacionTipoMovimientoService.enviarNotificacionTipoMovimiento_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idTipoMovimiento}")
    @Operation(summary = "Anular tipo de movimiento", description = "Cambia el estado del tipo de movimiento a inactivo")
    public ResponseEntity<ResponseEditarEstadoTipoMovimiento> anularTipoMovimiento(@PathVariable long idTipoMovimiento) {
        ResponseEditarEstadoTipoMovimiento response = edicionTipoMovimientoUseCase.AnularTipoMovimiento(idTipoMovimiento);

        if (response.isExito()) {
            NotificacionTipoMovimientoDTO notificacion = new NotificacionTipoMovimientoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Tipo de movimiento anulado");
            notificacion.setIdTipoMovimiento(idTipoMovimiento);

            notificacionTipoMovimientoService.enviarNotificacionTipoMovimiento_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idTipoMovimiento}/activar")
    @Operation(summary = "Activar tipo de movimiento by id", description = "Activa nuevamente un tipo de movimiento previamente anulado")
    public ResponseEntity<ResponseEditarEstadoTipoMovimiento> activarTipoMovimiento(@PathVariable long idTipoMovimiento) {
        ResponseEditarEstadoTipoMovimiento response = edicionTipoMovimientoUseCase.ActivarTipoMovimiento(idTipoMovimiento);

        if (response.isExito()) {
            NotificacionTipoMovimientoDTO notificacion = new NotificacionTipoMovimientoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Tipo de movimiento activado");
            notificacion.setIdTipoMovimiento(idTipoMovimiento);

            notificacionTipoMovimientoService.enviarNotificacionTipoMovimiento_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idTipoMovimiento}")
    @Operation(summary = "Detalle tipo de movimiento", description = "Obtiene el detalle de un tipo de movimiento")
    public ResponseEntity<ResponseDetalleTipoMovimiento> detalleTipoMovimiento(@PathVariable long idTipoMovimiento) {
        ResponseDetalleTipoMovimiento response = detalleTipoMovimientoUseCase.DetalleTipoMovimiento(idTipoMovimiento);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
