package com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionArticuloDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionArticuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/articulos")
public class ArticulosController {

    private final ListaArticuloUseCase listaArticuloUseCase;
    private final RegistroArticuloUseCase registroArticuloUseCase;
    private final EdicionAllArticuloUseCase edicionAllArticuloUseCase;
    private final EdicionEstadoArticuloUseCase edicionEstadoArticuloUseCase;
    private final DetalleArticuloUseCase detalleArticuloUseCase;
    private final NotificacionArticuloService notificacionArticuloService;

    public ArticulosController(ListaArticuloUseCase listaArticuloUseCase, RegistroArticuloUseCase registroArticuloUseCase, EdicionAllArticuloUseCase edicionAllArticuloUseCase, EdicionEstadoArticuloUseCase edicionEstadoArticuloUseCase, DetalleArticuloUseCase detalleArticuloUseCase, NotificacionArticuloService notificacionArticuloService) {
        this.listaArticuloUseCase = listaArticuloUseCase;
        this.registroArticuloUseCase = registroArticuloUseCase;
        this.edicionAllArticuloUseCase = edicionAllArticuloUseCase;
        this.edicionEstadoArticuloUseCase = edicionEstadoArticuloUseCase;
        this.detalleArticuloUseCase = detalleArticuloUseCase;
        this.notificacionArticuloService = notificacionArticuloService;
    }

    @GetMapping
    @Operation(summary = "Listar artículos by estado", description = "Obtiene la lista de artículos según su estado")
    public ResponseEntity<ResponseListaArticulo> listaArticulo(@Validated @ModelAttribute RequestListaArticulo request) {
        ResponseListaArticulo response = listaArticuloUseCase.ListaArticulo(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar artículo", description = "Permite registrar un nuevo artículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artículo registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroArticulo> registroArticulo(
            @Validated @RequestBody RequestRegistroArticulo request) {

        ResponseRegistroArticulo response = registroArticuloUseCase.RegistrarArticulo(request);

        if (response.isExito()) {
            NotificacionArticuloDTO notificacion = new NotificacionArticuloDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nuevo artículo registrado");

            notificacionArticuloService.enviarNotificacionArticulo_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @PutMapping
    @Operation(summary = "Editar Articulo", description = "Permite editar todos los datos de Articulo")
    public ResponseEntity<ResponseEditarAllArticulo> edicionAllArticulo(@Validated @RequestBody RequestEditarAllArticulo request) {
        ResponseEditarAllArticulo response = edicionAllArticuloUseCase.EdicionAllArticulo(request);

        if (response.isExito()) {
            NotificacionArticuloDTO notificacion = new NotificacionArticuloDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Registro de Articulo editado");
            notificacionArticuloService.enviarNotificacionArticulo_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idArticulo}")
    @Operation(summary = "Anular Articulo", description = "Cambia el estado de Articulo a inactivo")
    public ResponseEntity<ResponseEditarEstadoArticulo> anularArticulo(@PathVariable long idArticulo) {
        ResponseEditarEstadoArticulo response = edicionEstadoArticuloUseCase.AnularArticulo(idArticulo);

        if (response.isExito()) {
            NotificacionArticuloDTO notificacion = new NotificacionArticuloDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Registro de Articulo anulado");
            notificacion.setIdArticulos(idArticulo);
            notificacionArticuloService.enviarNotificacionArticulo_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idArticulo}/activar")
    @Operation(summary = "Activar Articulo", description = "Activa nuevamente un registro de Articulo previamente anulado")
    public ResponseEntity<ResponseEditarEstadoArticulo> activarArticulo(@PathVariable long idArticulo) {
        ResponseEditarEstadoArticulo response = edicionEstadoArticuloUseCase.ActivarArticulo(idArticulo);

        if (response.isExito()) {
            NotificacionArticuloDTO notificacion = new NotificacionArticuloDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Registro de Articulo activado");
            notificacion.setIdArticulos(idArticulo);
            notificacionArticuloService.enviarNotificacionArticulo_Activar(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idArticulo}")
    @Operation(summary = "Detalle Articulo", description = "Obtiene el detalle de Articulo")
    public ResponseEntity<ResponseDetalleArticulo> detalleArticulo(@PathVariable long idArticulo) {
        ResponseDetalleArticulo response = detalleArticuloUseCase.DetalleArticulo(idArticulo);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
