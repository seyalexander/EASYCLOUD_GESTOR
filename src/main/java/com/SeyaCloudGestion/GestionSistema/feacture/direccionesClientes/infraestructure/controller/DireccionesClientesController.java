package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionDireccionClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionDireccionClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/direccionClientes")
public class DireccionesClientesController {

    private final ListaDireccionesClientesUseCase listaDireccionesClientesUseCase;
    private final RegistroDireccionesClientesUseCase registroDireccionesClientesUseCase;
    private final EdicionDireccionesClientesUseCase edicionDireccionesClientesUseCase;
    private final EdicionDireccionesClientesEstadoUseCase edicionDireccionesClientesEstadoUseCase;
    private final DetalleDireccionesClientesUseCase detalleDireccionesClientesUseCase;
    private final NotificacionDireccionClienteService notificacionDireccionesClientesService;

    public DireccionesClientesController(
            ListaDireccionesClientesUseCase listaDireccionesClientesUseCase,
            RegistroDireccionesClientesUseCase registroDireccionesClientesUseCase,
            EdicionDireccionesClientesUseCase edicionDireccionesClientesUseCase,
            EdicionDireccionesClientesEstadoUseCase edicionDireccionesClientesEstadoUseCase,
            DetalleDireccionesClientesUseCase detalleDireccionesClientesUseCase,
            NotificacionDireccionClienteService notificacionDireccionesClientesService
            ) {
        this.listaDireccionesClientesUseCase = listaDireccionesClientesUseCase;
        this.registroDireccionesClientesUseCase = registroDireccionesClientesUseCase;
        this.edicionDireccionesClientesUseCase = edicionDireccionesClientesUseCase;
        this.edicionDireccionesClientesEstadoUseCase = edicionDireccionesClientesEstadoUseCase;
        this.detalleDireccionesClientesUseCase = detalleDireccionesClientesUseCase;
        this.notificacionDireccionesClientesService = notificacionDireccionesClientesService;
    }

    @GetMapping
    @Operation(summary = "Listar direcciones de clientes by estado", description = "Obtiene la lista de direcciones de clientes según su estado")
    public ResponseEntity<ResponseListaDireccionesClientes> listaDireccionesClientes(@Validated @ModelAttribute RequestListaDireccionesClientes request) {
        ResponseListaDireccionesClientes response =
                listaDireccionesClientesUseCase.ListaDireccionesClientes(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "Registrar dirección de cliente",
            description = "Permite registrar una nueva dirección de cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dirección de cliente registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroDireccionesClientes> registroDireccionesClientes(
            @Validated @RequestBody RequestRegistroDireccionesClientes request
    ) {
        ResponseRegistroDireccionesClientes response =
                registroDireccionesClientesUseCase.RegistroDireccionesClientes(request);

        if (response.isExito()) {
            NotificacionDireccionClienteDTO notificacion = new NotificacionDireccionClienteDTO();

            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nueva dirección de cliente registrada");

            notificacionDireccionesClientesService.enviarNotificacionDireccionCliente_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar dirección de cliente", description = "Permite editar todos los datos de una dirección de cliente existente")
    public ResponseEntity<ResponseEditarAllDireccionesClientes> edicionAllDireccionesClientes(@Validated @RequestBody RequestEditarAllDireccionesClientes request) {
        ResponseEditarAllDireccionesClientes response =
                edicionDireccionesClientesUseCase.EdicionAllDireccionesClientes(request);

        if (response.isExito()) {
            NotificacionDireccionClienteDTO notificacion = new NotificacionDireccionClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Dirección de cliente editada");

            notificacionDireccionesClientesService.enviarNotificacionDireccionCliente_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idDireccionCliente}")
    @Operation(
            summary = "Anular dirección de cliente",
            description = "Cambia el estado de la dirección de cliente a inactivo"
    )
    public ResponseEntity<ResponseEditarEstadoDireccionesClientes> anularDireccionesClientes(
            @PathVariable long idDireccionCliente
    ) {
        ResponseEditarEstadoDireccionesClientes response =
                edicionDireccionesClientesEstadoUseCase.AnularDireccionesClientes(idDireccionCliente);

        if (response.isExito()) {
            NotificacionDireccionClienteDTO notificacion = new NotificacionDireccionClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Dirección de cliente anulada");
            notificacion.setIdDireccionCliente(idDireccionCliente);

            notificacionDireccionesClientesService.enviarNotificacionDireccionCliente_Anular(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idDireccionCliente}/activar")
    @Operation(summary = "Activar dirección de cliente by id", description = "Activa nuevamente una dirección de cliente previamente anulada")
    public ResponseEntity<ResponseEditarEstadoDireccionesClientes> activarDireccionesClientes(@PathVariable long idDireccionCliente) {
        ResponseEditarEstadoDireccionesClientes response = edicionDireccionesClientesEstadoUseCase.ActivarDireccionesClientes(idDireccionCliente);

        if (response.isExito()) {
            NotificacionDireccionClienteDTO notificacion = new NotificacionDireccionClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Dirección de cliente activada");
            notificacion.setIdDireccionCliente(idDireccionCliente);

            notificacionDireccionesClientesService.enviarNotificacionDireccionCliente_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idDireccionCliente}")
    @Operation(summary = "Detalle dirección de cliente",description = "Obtiene el detalle de una dirección de cliente")
    public ResponseEntity<ResponseDetalleDireccionesClientes> detalleDireccionesClientes(
            @PathVariable long idDireccionCliente
    ) {
        ResponseDetalleDireccionesClientes response =
                detalleDireccionesClientesUseCase.DetalleDireccionesClientes(idDireccionCliente);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
