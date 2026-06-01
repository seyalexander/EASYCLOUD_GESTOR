package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.controller;
import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionProductoImpuestoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionProductoImpuestoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/productoImpuestos")
public class ProductoImpuestoController {

    private final ListaProductoImpuestoUseCase listaProductoImpuestoUseCase;
    private final RegistroProductoImpuestoUseCase registroProductoImpuestoUseCase;
    private final EdicionAllProductoImpuestoUseCase edicionAllProductoImpuestoUseCase;
    private final EdicionProductoImpuestoEstadoUseCase edicionProductoImpuestoEstadoUseCase;
    private final NotificacionProductoImpuestoService notificacionProductoImpuestoService;

    public ProductoImpuestoController(ListaProductoImpuestoUseCase listaProductoImpuestoUseCase, RegistroProductoImpuestoUseCase registroProductoImpuestoUseCase, EdicionAllProductoImpuestoUseCase edicionAllProductoImpuestoUseCase, EdicionProductoImpuestoEstadoUseCase edicionProductoImpuestoEstadoUseCase, NotificacionProductoImpuestoService notificacionProductoImpuestoService) {
        this.listaProductoImpuestoUseCase = listaProductoImpuestoUseCase;
        this.registroProductoImpuestoUseCase = registroProductoImpuestoUseCase;
        this.edicionAllProductoImpuestoUseCase = edicionAllProductoImpuestoUseCase;
        this.edicionProductoImpuestoEstadoUseCase = edicionProductoImpuestoEstadoUseCase;
        this.notificacionProductoImpuestoService = notificacionProductoImpuestoService;
    }

    @GetMapping
    @Operation(summary = "Listar impuestos de producto", description = "Obtiene la lista de impuestos de producto según los filtros enviados")
    public ResponseEntity<ResponseListaProductoImpuesto> listaProductoImpuesto(
            @Validated @ModelAttribute RequestListaProductoImpuesto request
    ) {
        ResponseListaProductoImpuesto response = listaProductoImpuestoUseCase.ListaProductoImpuesto(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar impuesto de producto", description = "Permite registrar un impuesto asociado a un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Impuesto de producto registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroProductoImpuesto> registroProductoImpuesto(
            @Validated @RequestBody RequestRegistroProductoImpuesto request
    ) {
        ResponseRegistroProductoImpuesto response = registroProductoImpuestoUseCase.RegistroProductoImpuesto(request);

        if (response.isExito()) {
            NotificacionProductoImpuestoDTO notificacion = new NotificacionProductoImpuestoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo impuesto de producto registrado");

            notificacionProductoImpuestoService.enviarNotificacionProductoImpuesto_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar impuesto de producto", description = "Permite editar todos los datos de un impuesto de producto existente")
    public ResponseEntity<ResponseEditarAllProductoImpuesto> editarAllProductoImpuesto(
            @Validated @RequestBody RequestEditarAllProductoImpuesto request
    ) {
        ResponseEditarAllProductoImpuesto response = edicionAllProductoImpuestoUseCase.EditarAllProductoImpuesto(request);

        if (response.isExito()) {
            NotificacionProductoImpuestoDTO notificacion = new NotificacionProductoImpuestoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Impuesto de producto editado");
            notificacion.setIdProductoImpuesto(request.getIdProductoImpuesto());

            notificacionProductoImpuestoService.enviarNotificacionProductoImpuesto_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idProductoImpuesto}")
    @Operation(summary = "Anular impuesto de producto", description = "Cambia el estado del impuesto de producto a inactivo")
    public ResponseEntity<ResponseEditarEstadoProductoImpuesto> anularProductoImpuesto(
            @PathVariable long idProductoImpuesto
    ) {
        ResponseEditarEstadoProductoImpuesto response = edicionProductoImpuestoEstadoUseCase.AnularProductoImpuesto(idProductoImpuesto);

        if (response.isExito()) {
            NotificacionProductoImpuestoDTO notificacion = new NotificacionProductoImpuestoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Impuesto de producto anulado");
            notificacion.setIdProductoImpuesto(idProductoImpuesto);

            notificacionProductoImpuestoService.enviarNotificacionProductoImpuesto_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idProductoImpuesto}/activar")
    @Operation(summary = "Activar impuesto de producto", description = "Activa nuevamente un impuesto de producto previamente anulado")
    public ResponseEntity<ResponseEditarEstadoProductoImpuesto> activarProductoImpuesto(
            @PathVariable long idProductoImpuesto
    ) {
        ResponseEditarEstadoProductoImpuesto response = edicionProductoImpuestoEstadoUseCase.ActivarProductoImpuesto(idProductoImpuesto);

        if (response.isExito()) {
            NotificacionProductoImpuestoDTO notificacion = new NotificacionProductoImpuestoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Impuesto de producto activado");
            notificacion.setIdProductoImpuesto(idProductoImpuesto);

            notificacionProductoImpuestoService.enviarNotificacionProductoImpuesto_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
