package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionAlmacenDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionAlmacenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/almacenes")
public class AlmacenController {
    private final ListaAlmacenUseCase listaAlmacenUseCase;
    private final RegistroAlmacenUseCase registroAlmacenUseCase;
    private final EdicionAllAlmacenUseCase edicionAllAlmacenUseCase;
    private final EdicionAlmacenEstadoUseCase edicionAlmacenEstadoUseCase;
    private final DetalleAlmacenUseCase detalleAlmacenUseCase;
    private final NotificacionAlmacenService notificacionAlmacenService;

    public AlmacenController(
            ListaAlmacenUseCase listaAlmacenUseCase,
            RegistroAlmacenUseCase registroAlmacenUseCase,
            EdicionAllAlmacenUseCase edicionAllAlmacenUseCase,
            EdicionAlmacenEstadoUseCase edicionAlmacenEstadoUseCase,
            DetalleAlmacenUseCase detalleAlmacenUseCase,
            NotificacionAlmacenService notificacionAlmacenService
            ) {
        this.listaAlmacenUseCase = listaAlmacenUseCase;
        this.registroAlmacenUseCase = registroAlmacenUseCase;
        this.edicionAllAlmacenUseCase = edicionAllAlmacenUseCase;
        this.edicionAlmacenEstadoUseCase = edicionAlmacenEstadoUseCase;
        this.detalleAlmacenUseCase = detalleAlmacenUseCase;
        this.notificacionAlmacenService = notificacionAlmacenService;
    }

    @GetMapping
    @Operation(summary = "Listar almacenes by estado", description = "Obtiene la lista de almacenes según su estado")
    public ResponseEntity<ResponseListaAlmacen> listaAlmacenes(@Validated @ModelAttribute RequestListaAlmacen request) {
        ResponseListaAlmacen response = listaAlmacenUseCase.ListaAlmacenes(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar almacén", description = "Permite registrar un nuevo almacén")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Almacén registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroAlmacen> registroAlmacenes(@Validated @RequestBody RequestRegistroAlmacen request) {
        ResponseRegistroAlmacen response = registroAlmacenUseCase.RegistroAlmacenes(request);

        if (response.isExito()) {
            NotificacionAlmacenDTO notificacion = new NotificacionAlmacenDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo almacén registrado");

            notificacionAlmacenService.enviarNotificacionAlmacen_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar almacén", description = "Permite editar todos los datos de un almacén existente")
    public ResponseEntity<ResponseEditarAllAlmacen> edicionAllAlmacenes(
            @Validated @RequestBody RequestEditarAllAlmacen request
    ) {
        ResponseEditarAllAlmacen response = edicionAllAlmacenUseCase.EdicionAllAlmacenes(request);

        if (response.isExito()) {
            NotificacionAlmacenDTO notificacion = new NotificacionAlmacenDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Almacén editado");
            notificacion.setIdAlmacen(request.getIdAlmacen());

            notificacionAlmacenService.enviarNotificacionAlmacen_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idAlmacenes}")
    @Operation(summary = "Anular almacén", description = "Cambia el estado del almacén a inactivo")
    public ResponseEntity<ResponseEditarEstadoAlmacen> anularAlmacenes(@PathVariable long idAlmacenes) {
        ResponseEditarEstadoAlmacen response = edicionAlmacenEstadoUseCase.AnularAlmacenes(idAlmacenes);

        if (response.isExito()) {
            NotificacionAlmacenDTO notificacion = new NotificacionAlmacenDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Almacén anulado");
            notificacion.setIdAlmacen(idAlmacenes);

            notificacionAlmacenService.enviarNotificacionAlmacen_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idAlmacenes}/activar")
    @Operation(summary = "Activar almacén by id", description = "Activa nuevamente un almacén previamente anulado")
    public ResponseEntity<ResponseEditarEstadoAlmacen> activarAlmacenes(@PathVariable long idAlmacenes) {
        ResponseEditarEstadoAlmacen response = edicionAlmacenEstadoUseCase.ActivarAlmacenes(idAlmacenes);

        if (response.isExito()) {
            NotificacionAlmacenDTO notificacion = new NotificacionAlmacenDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Almacén activado");
            notificacion.setIdAlmacen(idAlmacenes);

            notificacionAlmacenService.enviarNotificacionAlmacen_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idAlmacenes}")
    @Operation(summary = "Detalle almacén", description = "Obtiene el detalle de un almacén")
    public ResponseEntity<ResponseDetalleAlmacen> detalleAlmacenes(@PathVariable long idAlmacenes) {
        ResponseDetalleAlmacen response = detalleAlmacenUseCase.DetalleAlmacenes(idAlmacenes);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
