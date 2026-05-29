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
public class AlmacenesController {
    private final ListaAlmacenesUseCase listaAlmacenesUseCase;
    private final RegistroAlmacenesUseCase registroAlmacenesUseCase;
    private final EdicionAllAlmacenesUseCase edicionAllAlmacenesUseCase;
    private final EdicionAlmacenesEstadoUseCase edicionAlmacenesEstadoUseCase;
    private final DetalleAlmacenesUseCase detalleAlmacenesUseCase;
    private final NotificacionAlmacenService notificacionAlmacenService;

    public AlmacenesController(
            ListaAlmacenesUseCase listaAlmacenesUseCase,
            RegistroAlmacenesUseCase registroAlmacenesUseCase,
            EdicionAllAlmacenesUseCase edicionAllAlmacenesUseCase,
            EdicionAlmacenesEstadoUseCase edicionAlmacenesEstadoUseCase,
            DetalleAlmacenesUseCase detalleAlmacenesUseCase,
            NotificacionAlmacenService notificacionAlmacenService
            ) {
        this.listaAlmacenesUseCase = listaAlmacenesUseCase;
        this.registroAlmacenesUseCase = registroAlmacenesUseCase;
        this.edicionAllAlmacenesUseCase = edicionAllAlmacenesUseCase;
        this.edicionAlmacenesEstadoUseCase = edicionAlmacenesEstadoUseCase;
        this.detalleAlmacenesUseCase = detalleAlmacenesUseCase;
        this.notificacionAlmacenService = notificacionAlmacenService;
    }

    @GetMapping
    @Operation(summary = "Listar almacenes by estado", description = "Obtiene la lista de almacenes según su estado")
    public ResponseEntity<ResponseListaAlmacenes> listaAlmacenes(@Validated @ModelAttribute RequestListaAlmacenes request) {
        ResponseListaAlmacenes response = listaAlmacenesUseCase.ListaAlmacenes(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar almacén", description = "Permite registrar un nuevo almacén")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Almacén registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroAlmacenes> registroAlmacenes(@Validated @RequestBody RequestRegistroAlmacenes request) {
        ResponseRegistroAlmacenes response = registroAlmacenesUseCase.RegistroAlmacenes(request);

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
    public ResponseEntity<ResponseEditarAllAlmacenes> edicionAllAlmacenes(
            @Validated @RequestBody RequestEditarAllAlmacenes request
    ) {
        ResponseEditarAllAlmacenes response = edicionAllAlmacenesUseCase.EdicionAllAlmacenes(request);

        if (response.isExito()) {
            NotificacionAlmacenDTO notificacion = new NotificacionAlmacenDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Almacén editado");
            notificacion.setIdAlmacenes(request.getIdAlmacenes());

            notificacionAlmacenService.enviarNotificacionAlmacen_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idAlmacenes}")
    @Operation(summary = "Anular almacén", description = "Cambia el estado del almacén a inactivo")
    public ResponseEntity<ResponseEditarEstadoAlmacenes> anularAlmacenes(@PathVariable long idAlmacenes) {
        ResponseEditarEstadoAlmacenes response = edicionAlmacenesEstadoUseCase.AnularAlmacenes(idAlmacenes);

        if (response.isExito()) {
            NotificacionAlmacenDTO notificacion = new NotificacionAlmacenDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Almacén anulado");
            notificacion.setIdAlmacenes(idAlmacenes);

            notificacionAlmacenService.enviarNotificacionAlmacen_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idAlmacenes}/activar")
    @Operation(summary = "Activar almacén by id", description = "Activa nuevamente un almacén previamente anulado")
    public ResponseEntity<ResponseEditarEstadoAlmacenes> activarAlmacenes(@PathVariable long idAlmacenes) {
        ResponseEditarEstadoAlmacenes response = edicionAlmacenesEstadoUseCase.ActivarAlmacenes(idAlmacenes);

        if (response.isExito()) {
            NotificacionAlmacenDTO notificacion = new NotificacionAlmacenDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Almacén activado");
            notificacion.setIdAlmacenes(idAlmacenes);

            notificacionAlmacenService.enviarNotificacionAlmacen_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idAlmacenes}")
    @Operation(summary = "Detalle almacén", description = "Obtiene el detalle de un almacén")
    public ResponseEntity<ResponseDetalleAlmacenes> detalleAlmacenes(@PathVariable long idAlmacenes) {
        ResponseDetalleAlmacenes response = detalleAlmacenesUseCase.DetalleAlmacenes(idAlmacenes);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
