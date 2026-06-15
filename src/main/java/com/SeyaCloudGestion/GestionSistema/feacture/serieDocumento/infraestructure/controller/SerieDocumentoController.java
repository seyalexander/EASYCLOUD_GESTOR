package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSerieDocumentoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionSerieDocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/SerieDocumentos")
public class SerieDocumentoController {

    private final DetalleSerieDocumentoUseCase detalleSerieDocumentoUseCase;
    private final EdicionSerieDocumentoUseCase edicionSerieDocumentoUseCase;
    private final EdicionSerieDocumentoEstadoUseCase edicionSerieDocumentoEstadoUseCase;
    private final ListaSerieDocumentoUseCase listaSerieDocumentoUseCase;
    private final RegistroSerieDocumentoUseCase registroSerieDocumentoUseCase;
    private final NotificacionSerieDocumentoService notificacionSerieDocumentoService;

    public SerieDocumentoController(DetalleSerieDocumentoUseCase detalleSerieDocumentoUseCase, EdicionSerieDocumentoUseCase edicionSerieDocumentoUseCase, EdicionSerieDocumentoEstadoUseCase edicionSerieDocumentoEstadoUseCase, ListaSerieDocumentoUseCase listaSerieDocumentoUseCase, RegistroSerieDocumentoUseCase registroSerieDocumentoUseCase, NotificacionSerieDocumentoService notificacionSerieDocumentoService) {
        this.detalleSerieDocumentoUseCase = detalleSerieDocumentoUseCase;
        this.edicionSerieDocumentoUseCase = edicionSerieDocumentoUseCase;
        this.edicionSerieDocumentoEstadoUseCase = edicionSerieDocumentoEstadoUseCase;
        this.listaSerieDocumentoUseCase = listaSerieDocumentoUseCase;
        this.registroSerieDocumentoUseCase = registroSerieDocumentoUseCase;
        this.notificacionSerieDocumentoService = notificacionSerieDocumentoService;
    }
    @GetMapping
    @Operation(summary = "Listar series de documentos by estado", description = "Obtiene la lista de series de documentos según su estado")
    public ResponseEntity<ResponseListaSerieDocumento> listaSerieDocumento(
            @Validated @ModelAttribute RequestListaSeries request
    ) {
        ResponseListaSerieDocumento response = listaSerieDocumentoUseCase.ListaSerieDocumento(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "Registrar serie de documento",
            description = "Permite registrar una nueva serie de documento"
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serie de documento registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroSerieDocumento> registroSerieDocumento(
            @Validated @RequestBody RequestRegistroSeries request
    ) {
        ResponseRegistroSerieDocumento response = registroSerieDocumentoUseCase.RegistroSerieDocumento(request);

        if (response.isExito()) {
            NotificacionSerieDocumentoDTO notificacion = new NotificacionSerieDocumentoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nueva serie de documento registrada");

            notificacionSerieDocumentoService.enviarNotificacionSerieDocumento_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar serie de documento", description = "Permite editar todos los datos de una serie de documento existente")
    public ResponseEntity<ResponseEditarAllSerieDocumento> edicionAllSerieDocumento(@Validated @RequestBody RequestEditarAllSeries request) {
        ResponseEditarAllSerieDocumento response = edicionSerieDocumentoUseCase.EdicionAllSerieDocumento(request);

        if (response.isExito()) {
            NotificacionSerieDocumentoDTO notificacion = new NotificacionSerieDocumentoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Serie de documento editada");

            notificacionSerieDocumentoService.enviarNotificacionSerieDocumento_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idSerieDocumento}")
    @Operation(summary = "Anular serie de documento", description = "Cambia el estado de la serie de documento a inactivo")
    public ResponseEntity<ResponseEditarEstadoSerieDocumento> anularSerieDocumento(@PathVariable long idSerieDocumento) {
        ResponseEditarEstadoSerieDocumento response =
                edicionSerieDocumentoEstadoUseCase.AnularSerieDocumento(idSerieDocumento);

        if (response.isExito()) {
            NotificacionSerieDocumentoDTO notificacion = new NotificacionSerieDocumentoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Serie de documento anulada");
            notificacion.setIdSerieDocumento(idSerieDocumento);

            notificacionSerieDocumentoService.enviarNotificacionSerieDocumento_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idSerieDocumento}/activar")
    @Operation(summary = "Activar serie de documento by id", description = "Activa nuevamente una serie de documento previamente anulada")
    public ResponseEntity<ResponseEditarEstadoSerieDocumento> activarSerieDocumento(@PathVariable long idSerieDocumento) {
        ResponseEditarEstadoSerieDocumento response =
                edicionSerieDocumentoEstadoUseCase.ActivarSerieDocumento(idSerieDocumento);

        if (response.isExito()) {
            NotificacionSerieDocumentoDTO notificacion = new NotificacionSerieDocumentoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Serie de documento activada");
            notificacion.setIdSerieDocumento(idSerieDocumento);

            notificacionSerieDocumentoService.enviarNotificacionSerieDocumento_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idSerieDocumento}")
    @Operation(summary = "Detalle serie de documento", description = "Obtiene el detalle de una serie de documento")
    public ResponseEntity<ResponseDetalleSerieDocumento> detalleSerieDocumento(@PathVariable long idSerieDocumento) {
        ResponseDetalleSerieDocumento response = detalleSerieDocumentoUseCase.DetalleSerieDocumento(idSerieDocumento);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
