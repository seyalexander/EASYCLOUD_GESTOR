package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionListaPrecioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionListaPrecioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/listaPrecios")
public class ListaPreciosController {

    private final ListaListaPreciosUseCase listaListaPreciosUseCase;
    private final RegistroListaPreciosUseCase registroListaPreciosUseCase;
    private final EdicionAllListaPreciosUseCase edicionAllListaPreciosUseCase;
    private final EdicionListaPreciosEstadoUseCase edicionListaPreciosEstadoUseCase;
    private final DetalleListaPreciosUseCase detalleListaPreciosUseCase;
    private final NotificacionListaPrecioService notificacionListaPrecioService;

    public ListaPreciosController(ListaListaPreciosUseCase listaListaPreciosUseCase, RegistroListaPreciosUseCase registroListaPreciosUseCase, EdicionAllListaPreciosUseCase edicionAllListaPreciosUseCase, EdicionListaPreciosEstadoUseCase edicionListaPreciosEstadoUseCase, DetalleListaPreciosUseCase detalleListaPreciosUseCase, NotificacionListaPrecioService notificacionListaPrecioService) {
        this.listaListaPreciosUseCase = listaListaPreciosUseCase;
        this.registroListaPreciosUseCase = registroListaPreciosUseCase;
        this.edicionAllListaPreciosUseCase = edicionAllListaPreciosUseCase;
        this.edicionListaPreciosEstadoUseCase = edicionListaPreciosEstadoUseCase;
        this.detalleListaPreciosUseCase = detalleListaPreciosUseCase;
        this.notificacionListaPrecioService = notificacionListaPrecioService;
    }


    @GetMapping
    @Operation(summary = "Listar lista precios by estado", description = "Obtiene la lista de lista de precios según su estado")
    public ResponseEntity<ResponseListaListaPrecios> listaListaPrecios(@Validated @ModelAttribute RequestListarListaPrecios request) {
        ResponseListaListaPrecios response = listaListaPreciosUseCase.listaListaPrecios(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    @Operation(summary = "Registrar lista de precios", description = "Permite registrar una nueva lista de precios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista de precios registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroListaPrecios> registroListaPrecios(
            @Validated @RequestBody RequestRegistroListaPrecios request
    ) {
        ResponseRegistroListaPrecios response = registroListaPreciosUseCase.RegistroListaPrecios(request);

        if (response.isExito()) {
            NotificacionListaPrecioDTO notificacion = new NotificacionListaPrecioDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nueva lista de precios registrada");

            notificacionListaPrecioService.enviarNotificacionListaPrecio_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar lista de precios", description = "Permite editar todos los datos de una lista de precios existente")
    public ResponseEntity<ResponseEditarAllListaPrecios> editarAllListaPrecios(@Validated @RequestBody RequestEditarAllListaPrecios request) {
        ResponseEditarAllListaPrecios response = edicionAllListaPreciosUseCase.EditarAllListaPrecios(request);

        if (response.isExito()) {
            NotificacionListaPrecioDTO notificacion = new NotificacionListaPrecioDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Lista de precios editada");

            notificacionListaPrecioService.enviarNotificacionListaPrecio_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idListaPrecio}")
    @Operation(summary = "Anular lista de precios", description = "Cambia el estado de la lista de precios a inactivo")
    public ResponseEntity<ResponseEditarEstadoListaPrecios> anularListaPrecios(@PathVariable long idListaPrecio) {
        ResponseEditarEstadoListaPrecios response = edicionListaPreciosEstadoUseCase.AnularListaPrecios(idListaPrecio);

        if (response.isExito()) {
            NotificacionListaPrecioDTO notificacion = new NotificacionListaPrecioDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Lista de precios anulada");
            notificacion.setIdListaPrecio(idListaPrecio);
            notificacionListaPrecioService.enviarNotificacionListaPrecio_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idListaPrecio}/activar")
    @Operation(summary = "Activar lista de precios by id", description = "Activa nuevamente una lista de precios previamente anulada")
    public ResponseEntity<ResponseEditarEstadoListaPrecios> activarListaPrecios(@PathVariable long idListaPrecio) {
        ResponseEditarEstadoListaPrecios response = edicionListaPreciosEstadoUseCase.ActivarListaPrecios(idListaPrecio);

        if (response.isExito()) {
            NotificacionListaPrecioDTO notificacion = new NotificacionListaPrecioDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Lista de precios activada");
            notificacion.setIdListaPrecio(idListaPrecio);
            notificacionListaPrecioService.enviarNotificacionListaPrecio_Activar(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idListaPrecio}")
    @Operation(summary = "Detalle lista de precios", description = "Obtiene el detalle de una lista de precios")
    public ResponseEntity<ResponseDetalleListaPrecios> detalleListaPrecios(@PathVariable long idListaPrecio) {

        ResponseDetalleListaPrecios response = detalleListaPreciosUseCase.DetalleListaPrecios(idListaPrecio);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
