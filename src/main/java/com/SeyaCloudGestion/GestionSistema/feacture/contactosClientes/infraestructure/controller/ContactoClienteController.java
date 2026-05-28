package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionContactoClienteDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionContactoClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/contactosClientes")
public class ContactoClienteController {
    private final ListaContactoClienteUseCase listaContactoClienteUseCase;
    private final RegistroContactoClienteUseCase registroContactoClienteUseCase;
    private final EdicionContactoAllClienteUseCase edicionContactoClienteUseCase;
    private final DetalleContactoClienteUseCase detalleContactoClienteUseCase;
    private final EdicionContactoClienteEstadoUseCase edicionContactoClienteEstadoUseCase;
    private final NotificacionContactoClienteService notificacionClienteService;

    public ContactoClienteController(ListaContactoClienteUseCase listaContactoClienteUseCase, RegistroContactoClienteUseCase registroContactoClienteUseCase, EdicionContactoAllClienteUseCase edicionContactoClienteUseCase, DetalleContactoClienteUseCase detalleContactoClienteUseCase, EdicionContactoClienteEstadoUseCase edicionContactoClienteEstadoUseCase, NotificacionContactoClienteService notificacionClienteService, NotificacionContactoClienteService notificacionClienteService1) {
        this.listaContactoClienteUseCase = listaContactoClienteUseCase;
        this.registroContactoClienteUseCase = registroContactoClienteUseCase;
        this.edicionContactoClienteUseCase = edicionContactoClienteUseCase;
        this.detalleContactoClienteUseCase = detalleContactoClienteUseCase;
        this.edicionContactoClienteEstadoUseCase = edicionContactoClienteEstadoUseCase;
        this.notificacionClienteService = notificacionClienteService1;
    }

    @GetMapping
    @Operation(summary = "Listar contactos de clientes by estado", description = "Obtiene la lista de contactos de clientes según su estado")
    public ResponseEntity<ResponseListaContactoCliente> listaContactoCliente(
            @Validated @ModelAttribute RequestListaContactoCliente request
    ) {
        ResponseListaContactoCliente response = listaContactoClienteUseCase.ListaContactoCliente(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar contacto de cliente", description = "Permite registrar un nuevo contacto de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contacto de cliente registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroContactoCliente> registroContactoCliente(
            @Validated @RequestBody RequestRegistroContactoCliente request
    ) {
        ResponseRegistroContactoCliente response = registroContactoClienteUseCase.RegistroContactoCliente(request);

        if (response.isExito()) {
            NotificacionContactoClienteDTO notificacion = new NotificacionContactoClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo contacto de cliente registrado");
            notificacionClienteService.enviarNotificacionContactoCliente_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar contacto de cliente", description = "Permite editar todos los datos de un contacto de cliente existente")
    public ResponseEntity<ResponseEditarAllContactoCliente> edicionAllContactoCliente(
            @Validated @RequestBody RequestEditarAllContactoCliente request
    ) {
        ResponseEditarAllContactoCliente response = edicionContactoClienteUseCase.EdicionAllContactoCliente(request);

        if (response.isExito()) {
            NotificacionContactoClienteDTO notificacion = new NotificacionContactoClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Contacto de cliente editado");
            notificacionClienteService.enviarNotificacionContactoCliente_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idContactoCliente}")
    @Operation(summary = "Anular contacto de cliente", description = "Cambia el estado del contacto de cliente a inactivo")
    public ResponseEntity<ResponseEditarEstadoContactoCliente> anularContactoCliente(
            @PathVariable long idContactoCliente
    ) {
        ResponseEditarEstadoContactoCliente response = edicionContactoClienteEstadoUseCase.AnularContactoCliente(idContactoCliente);

        if (response.isExito()) {
            NotificacionContactoClienteDTO notificacion = new NotificacionContactoClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Contacto de cliente anulado");
            notificacionClienteService.enviarNotificacionContactoCliente_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idContactoCliente}/activar")
    @Operation(summary = "Activar contacto de cliente by id", description = "Activa nuevamente un contacto de cliente previamente anulado")
    public ResponseEntity<ResponseEditarEstadoContactoCliente> activarContactoCliente(
            @PathVariable long idContactoCliente
    ) {
        ResponseEditarEstadoContactoCliente response = edicionContactoClienteEstadoUseCase.ActivarContactoCliente(idContactoCliente);

        if (response.isExito()) {
            NotificacionContactoClienteDTO notificacion = new NotificacionContactoClienteDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Contacto de cliente activado");
            notificacionClienteService.enviarNotificacionContactoCliente_Activar(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idContactoCliente}")
    @Operation(summary = "Detalle contacto de cliente", description = "Obtiene el detalle de un contacto de cliente")
    public ResponseEntity<ResponseDetalleContactoCliente> detalleContactoCliente(
            @PathVariable long idContactoCliente
    ) {
        ResponseDetalleContactoCliente response = detalleContactoClienteUseCase.DetalleContactoCliente(idContactoCliente);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
