package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.useCase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/Proveedores")
public class ProveedoresController {

    @Autowired
    private ListaProveedorUseCase listaProveedorUseCase;

    @Autowired
    private RegistroProveedorUseCase registroProveedorUseCase;

    @Autowired
    private EdicionProveedorUseCase edicionProveedorUseCase;

    @Autowired
    private EdicionProveedorEstadoUseCase edicionProveedorEstadoUseCase;

    @Autowired
    private DetalleProveedorUseCase detalleProveedorUseCase;

    // Descomentar si implementas WebSockets para proveedores
    // @Autowired
    // private NotificacionProveedorService notificacionProveedorService;

    @GetMapping
    @Operation(summary = "Listar proveedores by estado", description = "Obtiene la lista de proveedores según su estado")
    public ResponseEntity<ResponseListaProveedor> listaProveedores(@Validated @ModelAttribute RequestListaProveedor request) {
        ResponseListaProveedor response = listaProveedorUseCase.ListaProveedores(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar proveedor", description = "Permite registrar un nuevo proveedor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroProveedor> registroProveedor(
            @Validated @RequestBody RequestRegistroProveedor request) {

        ResponseRegistroProveedor response = registroProveedorUseCase.RegistroProveedores(request);

        if (response.isExito()) {
            // Ejemplo de notificación opcional por WebSockets:
            // NotificacionProveedorDTO notificacion = new NotificacionProveedorDTO("REGISTRO", "Nuevo proveedor registrado");
            // notificacionProveedorService.enviarNotificacionProveedor_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar proveedor", description = "Permite editar todos los datos de un proveedor existente")
    public ResponseEntity<ResponseEditarAllProveedor> edicionAllProveedor(
            @Validated @RequestBody RequestEditarAllProveedor request) {

        ResponseEditarAllProveedor response = edicionProveedorUseCase.EdicionAllProveedores(request);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idProveedor}")
    @Operation(summary = "Anular proveedor", description = "Cambia el estado del proveedor a inactivo")
    public ResponseEntity<ResponseEditarEstadoProveedor> anularProveedor(@PathVariable long idProveedor) {

        ResponseEditarEstadoProveedor response = edicionProveedorEstadoUseCase.EdicionAnularProveedor(idProveedor);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idProveedor}/activar")
    @Operation(summary = "Activar proveedor by id", description = "Activa nuevamente un proveedor previamente anulado")
    public ResponseEntity<ResponseEditarEstadoProveedor> activarProveedor(@PathVariable long idProveedor) {

        ResponseEditarEstadoProveedor response = edicionProveedorEstadoUseCase.EdicionActivarProveedor(idProveedor);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idProveedor}")
    @Operation(summary = "Detalle proveedor", description = "Obtiene el detalle de un proveedor específico")
    public ResponseEntity<ResponseDetalleProveedor> detalleProveedor(@PathVariable long idProveedor) {

        ResponseDetalleProveedor response = detalleProveedorUseCase.DetalleProveedores(idProveedor);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}