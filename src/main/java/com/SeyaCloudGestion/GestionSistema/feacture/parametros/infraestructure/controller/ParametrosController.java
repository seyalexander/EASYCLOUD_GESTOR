package com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionParametroDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionParametroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/parametros")
public class ParametrosController {

    private final ListaParametrosUseCase listaParametrosUseCase;
    private final RegistroParametrosUseCase registroParametrosUseCase;
    private final EdicionAllParametrosUseCase edicionAllParametrosUseCase;
    private final DetalleParametrosUseCase detalleParametrosUseCase;
    private final EdicionParametrosEstadoUseCase edicionParametrosEstadoUseCase;
    private final NotificacionParametroService notificacionParametroService;

    public ParametrosController(ListaParametrosUseCase listaParametrosUseCase, RegistroParametrosUseCase registroParametrosUseCase, EdicionAllParametrosUseCase edicionAllParametrosUseCase, DetalleParametrosUseCase detalleParametrosUseCase, EdicionParametrosEstadoUseCase edicionParametrosEstadoUseCase, NotificacionParametroService notificacionParametroService) {
        this.listaParametrosUseCase = listaParametrosUseCase;
        this.registroParametrosUseCase = registroParametrosUseCase;
        this.edicionAllParametrosUseCase = edicionAllParametrosUseCase;
        this.detalleParametrosUseCase = detalleParametrosUseCase;
        this.edicionParametrosEstadoUseCase = edicionParametrosEstadoUseCase;
        this.notificacionParametroService = notificacionParametroService;
    }
    @GetMapping
    @Operation(summary = "Listar parámetros by estado", description = "Obtiene la lista de parámetros según su estado")
    public ResponseEntity<ResponseListaParametros> listaParametro(
            @Validated @ModelAttribute RequestListaParametros request
    ) {
        ResponseListaParametros response = listaParametrosUseCase.ListaParametro(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar parámetro", description = "Permite registrar un nuevo parámetro del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parámetro registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroParametros> registroParametro(
            @Validated @RequestBody RequestRegistroParametros request
    ) {
        ResponseRegistroParametros response = registroParametrosUseCase.RegistroParametro(request);

        if (response.isExito()) {
            NotificacionParametroDTO notificacion = new NotificacionParametroDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo parámetro registrado");

            notificacionParametroService.enviarNotificacionParametro_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar parámetro", description = "Permite editar todos los datos de un parámetro existente")
    public ResponseEntity<ResponseEditarAllParametros> edicionAllParametro(
            @Validated @RequestBody RequestEditarAllParametros request
    ) {
        ResponseEditarAllParametros response = edicionAllParametrosUseCase.EditarAllParametros(request);

        if (response.isExito()) {
            NotificacionParametroDTO notificacion = new NotificacionParametroDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Parámetro editado");

            notificacionParametroService.enviarNotificacionParametro_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idParametroSistema}")
    @Operation(summary = "Anular parámetro", description = "Cambia el estado del parámetro a inactivo")
    public ResponseEntity<ResponseEditarEstadoParametros> anularParametro(
            @PathVariable long idParametroSistema
    ) {
        ResponseEditarEstadoParametros response = edicionParametrosEstadoUseCase.AnularParametro(idParametroSistema);

        if (response.isExito()) {
            NotificacionParametroDTO notificacion = new NotificacionParametroDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Parámetro anulado");
            notificacion.setIdParametroSistema(idParametroSistema);

            notificacionParametroService.enviarNotificacionParametro_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idParametroSistema}/activar")
    @Operation(summary = "Activar parámetro by id", description = "Activa nuevamente un parámetro previamente anulado")
    public ResponseEntity<ResponseEditarEstadoParametros> activarParametro(
            @PathVariable long idParametroSistema
    ) {
        ResponseEditarEstadoParametros response = edicionParametrosEstadoUseCase.ActivarParametro(idParametroSistema);

        if (response.isExito()) {
            NotificacionParametroDTO notificacion = new NotificacionParametroDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Parámetro activado");
            notificacion.setIdParametroSistema(idParametroSistema);

            notificacionParametroService.enviarNotificacionParametro_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idParametroSistema}")
    @Operation(summary = "Detalle parámetro", description = "Obtiene el detalle de un parámetro del sistema")
    public ResponseEntity<ResponseDetalleParametros> detalleParametro(
            @PathVariable long idParametroSistema
    ) {
        ResponseDetalleParametros response = detalleParametrosUseCase.DetalleParametro(idParametroSistema);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
