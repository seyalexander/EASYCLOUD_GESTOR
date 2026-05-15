package com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestEditarAllEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestListaEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestRegistroEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase.EdicionEstadoTipoDocumentoUseCase;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionEmpleadoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionEmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/empleado")
public class EmpleadoController {

    @Autowired
    private DetalleEmpleadoUseCase detalleEmpleadoUseCase;
    @Autowired
    private ListaEmpleadoUseCase listaEmpleadoUseCase;
    @Autowired
    private RegistroEmpleadoUseCase registroEmpleadoUseCase;
    @Autowired
    private EdicionEmpleadoEstadoUseCase edicionEmpleadoEstadoUseCase;
    @Autowired
    private EdicionEmpleadoUseCase edicionEmpleadoUseCase;
    @Autowired
    private NotificacionEmpleadoService NotificacionEmpleadoService;
    @Autowired
    private EdicionEstadoTipoDocumentoUseCase edicionEstadoTipoDocumentoUseCase;

    @GetMapping
    @Operation(summary = "Listar empleados by estado", description = "Obtiene la lista de empleados según su estado")
    public ResponseEntity<ResponseListaEmpleado> listaEmplados(@Validated @ModelAttribute RequestListaEmpleado request) {

        ResponseListaEmpleado response = listaEmpleadoUseCase.ListarEmpleado(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar empleados", description = "Permite registrar un nuevo empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroEmpleado> registroEmpleado(
            @Validated @RequestBody RequestRegistroEmpleado request) {

        ResponseRegistroEmpleado response = registroEmpleadoUseCase.RegistrorEmpleado(request);

        if (response.isExito()) {
            NotificacionEmpleadoDTO notificacion = new NotificacionEmpleadoDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nuevo empleado registrado");

            NotificacionEmpleadoService.enviarNotificacionEmpleado_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar empleado", description = "Permite editar todos los datos de una familia existente")
    public ResponseEntity<ResponseEditarAllEmpleado> edicionAllEmpleado(
            @Validated @RequestBody RequestEditarAllEmpleado request) {

        ResponseEditarAllEmpleado response = edicionEmpleadoUseCase.EditarEmpleado(request);

        if (response.isExito()) {
            NotificacionEmpleadoDTO notificacion = new NotificacionEmpleadoDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Empleado editado");

            NotificacionEmpleadoService.enviarNotificacionEmpleado_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idEmpleado}")
    @Operation(summary = "Anular empleado", description = "Cambia el estado de un empleado a inactivo")
    public ResponseEntity<ResponseEditarEstadoEmpleado> anularEmpleado(@PathVariable long idEmpleado) {

        ResponseEditarEstadoEmpleado response = edicionEmpleadoEstadoUseCase.AnularEmpleado(idEmpleado);

        if (response.isExito()) {
            NotificacionEmpleadoDTO notificacion = new NotificacionEmpleadoDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Empleado anulada");
            notificacion.setIdEmpleado(idEmpleado);

            NotificacionEmpleadoService.enviarNotificacionEmpleado_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idEmpleado}/activar")
    @Operation(summary = "Activar empleado by id", description = "Activa nuevamente un empleado previamente anulado")
    public ResponseEntity<ResponseEditarEstadoEmpleado> activarEmpleado(@PathVariable long idEmpleado) {

        ResponseEditarEstadoEmpleado response = edicionEmpleadoEstadoUseCase.ActivarEmpleado(idEmpleado);

        if (response.isExito()) {
            NotificacionEmpleadoDTO notificacion = new NotificacionEmpleadoDTO();
            notificacion.setTipo("ACTIVACION");
            notificacion.setMensaje("Empleado activado");
            notificacion.setIdEmpleado(idEmpleado);

            NotificacionEmpleadoService.enviarNotificacionEmpleado_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idEmpleado}")
    @Operation(summary = "Detalle Empleado", description = "Obtiene el detalle de un empleado")
    public ResponseEntity<ResponseDetalleEmpleado> detalleEmpleado(@PathVariable long idEmpleado) {

        ResponseDetalleEmpleado response = detalleEmpleadoUseCase.DetalleEmpleado(idEmpleado);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
