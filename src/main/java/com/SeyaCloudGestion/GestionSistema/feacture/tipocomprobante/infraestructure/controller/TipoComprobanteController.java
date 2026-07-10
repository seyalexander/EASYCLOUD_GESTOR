package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.controller;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoComprobanteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionTipoComprobanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tipocomprobante")
public class TipoComprobanteController {

    private final ListaTipoComprobanteUseCase listaTipoComprobanteUseCase;
    private final RegistroTipoComprobanteUseCase registroTipoComprobanteUseCase;
    private final EdicionTipoComprobanteAllUseCase edicionTipoComprobanteAllUseCase;
    private final EdicionTipoComprobanteEstadoUseCase edicionTipoComprobanteEstadoUseCase;
    private final DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase;
    private final NotificacionTipoComprobanteService notificacionTipoComprobanteService;

    public TipoComprobanteController(
            ListaTipoComprobanteUseCase listaTipoComprobanteUseCase,
            RegistroTipoComprobanteUseCase registroTipoComprobanteUseCase,
            EdicionTipoComprobanteAllUseCase edicionTipoComprobanteAllUseCase,
            EdicionTipoComprobanteEstadoUseCase edicionTipoComprobanteEstadoUseCase,
            DetalleTipoComprobanteUseCase detalleTipoComprobanteUseCase,
            NotificacionTipoComprobanteService notificacionTipoComprobanteService
    ) {
        this.listaTipoComprobanteUseCase = listaTipoComprobanteUseCase;
        this.registroTipoComprobanteUseCase = registroTipoComprobanteUseCase;
        this.edicionTipoComprobanteAllUseCase = edicionTipoComprobanteAllUseCase;
        this.edicionTipoComprobanteEstadoUseCase = edicionTipoComprobanteEstadoUseCase;
        this.detalleTipoComprobanteUseCase = detalleTipoComprobanteUseCase;
        this.notificacionTipoComprobanteService = notificacionTipoComprobanteService;
    }

    @GetMapping
    @Operation(summary = "Listar tipos de comprobante by estado", description = "Obtiene la lista de tipos de comprobante según su estado")
    public ResponseEntity<ResponseListaTipoComprobante> listaTipoComprobante(@Validated @ModelAttribute RequestListaTipoComprobante request) {

        ResponseListaTipoComprobante response = listaTipoComprobanteUseCase.ListaTipoComprobante(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar tipo de comprobante", description = "Permite registrar un nuevo tipo de comprobante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de comprobante registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroTipoComprobante> registroTipoComprobante(
            @Validated @RequestBody RequestRegistroTipoComprobante request) {

        ResponseRegistroTipoComprobante response = registroTipoComprobanteUseCase.RegistroTipoComprobante(request);

        if (response.isExito()) {
            NotificacionTipoComprobanteDTO notificacion = new NotificacionTipoComprobanteDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nuevo tipo de comprobante registrado");

            notificacionTipoComprobanteService.enviarNotificacionTipoComprobante_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar tipo de comprobante", description = "Permite editar todos los datos de un tipo de comprobante existente")
    public ResponseEntity<ResponseEditarAllTipoComprobante> edicionAllTipoComprobante(
            @Validated @RequestBody RequestEditarAllTipoComprobante request) {

        ResponseEditarAllTipoComprobante response = edicionTipoComprobanteAllUseCase.EdicionAllTipoComprobante(request);

        if (response.isExito()) {
            NotificacionTipoComprobanteDTO notificacion = new NotificacionTipoComprobanteDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Tipo de comprobante editado");

            notificacionTipoComprobanteService.enviarNotificacionTipoComprobante_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idTipoComprobante}")
    @Operation(summary = "Anular tipo de comprobante", description = "Cambia el estado del tipo de comprobante a inactivo")
    public ResponseEntity<ResponseEditarEstadoTipoComprobante> anularTipoComprobante(@PathVariable long idTipoComprobante) {

        ResponseEditarEstadoTipoComprobante response = edicionTipoComprobanteEstadoUseCase.EdicionAnularTipoComprobante(idTipoComprobante);

        if (response.isExito()) {
            NotificacionTipoComprobanteDTO notificacion = new NotificacionTipoComprobanteDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Tipo de comprobante ';anulado");
            notificacion.setIdTipoComprobante(idTipoComprobante);

            notificacionTipoComprobanteService.enviarNotificacionTipoComprobante_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idTipoComprobante}/activar")
    @Operation(summary = "Activar tipo de comprobante by id", description = "Activa nuevamente un tipo de comprobante previamente anulado")
    public ResponseEntity<ResponseEditarEstadoTipoComprobante> activarTipoComprobante(@PathVariable long idTipoComprobante) {

        ResponseEditarEstadoTipoComprobante response = edicionTipoComprobanteEstadoUseCase.EdicionActivarTipoComprobante(idTipoComprobante);

        if (response.isExito()) {
            NotificacionTipoComprobanteDTO notificacion = new NotificacionTipoComprobanteDTO();
            notificacion.setTipo("ACTIVACION");
            notificacion.setMensaje("Tipo de comprobante activado");
            notificacion.setIdTipoComprobante(idTipoComprobante);

            notificacionTipoComprobanteService.enviarNotificacionTipoComprobante_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idTipoComprobante}")
    @Operation(summary = "Detalle tipo de comprobante", description = "Obtiene el detalle de un tipo de comprobante")
    public ResponseEntity<ResponseDetalleTipoComprobante> detalleTipoComprobante(@PathVariable long idTipoComprobante) {

        ResponseDetalleTipoComprobante response = detalleTipoComprobanteUseCase.DetalleTipoComprobante(idTipoComprobante);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}