package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.controller;
import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionProductoPrecioDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionProductoPrecioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/productoPrecios")
public class ProductoPrecioController {

    private final ListaProductoPrecioUseCase listaProductoPrecioUseCase;
    private final RegistroProductoPrecioUseCase registroProductoPrecioUseCase;
    private final EdicionAllProductoPrecioUseCase edicionAllProductoPrecioUseCase;
    private final EdicionProductoPrecioEstadoUseCase edicionProductoPrecioEstadoUseCase;
    private final NotificacionProductoPrecioService notificacionProductoPrecioService;

    public ProductoPrecioController(ListaProductoPrecioUseCase listaProductoPrecioUseCase, RegistroProductoPrecioUseCase registroProductoPrecioUseCase, EdicionAllProductoPrecioUseCase edicionAllProductoPrecioUseCase, EdicionProductoPrecioEstadoUseCase edicionProductoPrecioEstadoUseCase, NotificacionProductoPrecioService notificacionProductoPrecioService) {
        this.listaProductoPrecioUseCase = listaProductoPrecioUseCase;
        this.registroProductoPrecioUseCase = registroProductoPrecioUseCase;
        this.edicionAllProductoPrecioUseCase = edicionAllProductoPrecioUseCase;
        this.edicionProductoPrecioEstadoUseCase = edicionProductoPrecioEstadoUseCase;
        this.notificacionProductoPrecioService = notificacionProductoPrecioService;
    }

    @GetMapping
    @Operation(summary = "Listar precios de productos", description = "Obtiene la lista de precios de productos según los filtros enviados")
    public ResponseEntity<ResponseListaProductoPrecio> listarProductoPrecio(
            @Validated @ModelAttribute RequestListaProductoPrecio request
    ) {
        ResponseListaProductoPrecio response = listaProductoPrecioUseCase.ListarProductoPrecio(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar precio de producto", description = "Permite registrar un nuevo precio para un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Precio de producto registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroProductoPrecio> registroProductoPrecio(
            @Validated @RequestBody RequestRegistroProductoPrecio request
    ) {
        ResponseRegistroProductoPrecio response = registroProductoPrecioUseCase.RegistroProductoPrecio(request);

        if (response.isExito()) {
            NotificacionProductoPrecioDTO notificacion = new NotificacionProductoPrecioDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo precio de producto registrado");
            notificacion.setIdArticulo(request.getIdArticulo());
            notificacion.setIdListaPrecio(request.getIdListaPrecio());

            notificacionProductoPrecioService.enviarNotificacionProductoPrecio_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar precio de producto", description = "Permite editar todos los datos de un precio de producto existente")
    public ResponseEntity<ResponseEditarAllProductoPrecio> editarAllProductoPrecio(
            @Validated @RequestBody RequestEditarAllProductoPrecio request
    ) {
        ResponseEditarAllProductoPrecio response = edicionAllProductoPrecioUseCase.EditarAllProductoPrecio(request);

        if (response.isExito()) {
            NotificacionProductoPrecioDTO notificacion = new NotificacionProductoPrecioDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Precio de producto editado");
            notificacion.setIdProductoPrecio(request.getIdProductoPrecio());
            notificacion.setIdArticulo(request.getIdArticulo());
            notificacion.setIdListaPrecio(request.getIdListaPrecio());

            notificacionProductoPrecioService.enviarNotificacionProductoPrecio_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idProductoPrecio}")
    @Operation(summary = "Anular precio de producto", description = "Cambia el estado del precio de producto a inactivo")
    public ResponseEntity<ResponseEditarEstadoProductoPrecio> anularProductoPrecio(
            @PathVariable long idProductoPrecio
    ) {
        ResponseEditarEstadoProductoPrecio response = edicionProductoPrecioEstadoUseCase.AnularProductoPrecio(idProductoPrecio);

        if (response.isExito()) {
            NotificacionProductoPrecioDTO notificacion = new NotificacionProductoPrecioDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Precio de producto anulado");
            notificacion.setIdProductoPrecio(idProductoPrecio);

            notificacionProductoPrecioService.enviarNotificacionProductoPrecio_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idProductoPrecio}/activar")
    @Operation(summary = "Activar precio de producto", description = "Activa nuevamente un precio de producto previamente anulado")
    public ResponseEntity<ResponseEditarEstadoProductoPrecio> activarProductoPrecio(
            @PathVariable long idProductoPrecio
    ) {
        ResponseEditarEstadoProductoPrecio response = edicionProductoPrecioEstadoUseCase.ActivarProductoPrecio(idProductoPrecio);

        if (response.isExito()) {
            NotificacionProductoPrecioDTO notificacion = new NotificacionProductoPrecioDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Precio de producto activado");
            notificacion.setIdProductoPrecio(idProductoPrecio);

            notificacionProductoPrecioService.enviarNotificacionProductoPrecio_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
