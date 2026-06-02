package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestEditarAllCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestListaCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestListaCuentasPorCobrarIDCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestRegistroCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCuentasPorCobrarDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionCuentasPorCobrarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/cuentasPorCobrar")
public class CuentasPorCobrarController {
    private final ListaCuentasPorCobrarUseCase listaCuentasPorCobrarUseCase;
    private final RegistroCuentasPorCobrarUseCase registroCuentasPorCobrarUseCase;
    private final EdicionAllCuentasPorCobrarUseCase edicionAllCuentasPorCobrarUseCase;
    private final EdicionCuentasPorCobrarEstadoUseCase edicionCuentasPorCobrarEstadoUseCase;
    private final DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase;
    private final NotificacionCuentasPorCobrarService notificacionCuentasPorCobrarService;

    public CuentasPorCobrarController(ListaCuentasPorCobrarUseCase listaCuentasPorCobrarUseCase, RegistroCuentasPorCobrarUseCase registroCuentasPorCobrarUseCase, EdicionAllCuentasPorCobrarUseCase edicionAllCuentasPorCobrarUseCase, EdicionCuentasPorCobrarEstadoUseCase edicionCuentasPorCobrarEstadoUseCase, DetalleCuentasPorCobrarUseCase detalleCuentasPorCobrarUseCase, NotificacionCuentasPorCobrarService notificacionCuentasPorCobrarService) {
        this.listaCuentasPorCobrarUseCase = listaCuentasPorCobrarUseCase;
        this.registroCuentasPorCobrarUseCase = registroCuentasPorCobrarUseCase;
        this.edicionAllCuentasPorCobrarUseCase = edicionAllCuentasPorCobrarUseCase;
        this.edicionCuentasPorCobrarEstadoUseCase = edicionCuentasPorCobrarEstadoUseCase;
        this.detalleCuentasPorCobrarUseCase = detalleCuentasPorCobrarUseCase;
        this.notificacionCuentasPorCobrarService = notificacionCuentasPorCobrarService;
    }

    @GetMapping
    @Operation(summary = "Listar cuentas por cobrar", description = "Obtiene la lista de cuentas por cobrar según los filtros enviados")
    public ResponseEntity<ResponseListaCuentasPorCobrar> listaCuentasPorCobrar(
            @Validated @ModelAttribute RequestListaCuentasPorCobrar request
    ) {
        ResponseListaCuentasPorCobrar response = listaCuentasPorCobrarUseCase.ListaCuentasPorCobrar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar cuentas por cobrar", description = "Obtiene la lista de cuentas por cobrar según los filtros enviados")
    public ResponseEntity<ResponseListaCuentasPorCobrar> ListaCuentasPorCobrarIDCliente(
            @Validated @ModelAttribute RequestListaCuentasPorCobrarIDCliente request
    ) {
        ResponseListaCuentasPorCobrar response = listaCuentasPorCobrarUseCase.ListaCuentasPorCobrarIDCliente(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar cuenta por cobrar", description = "Permite registrar una nueva cuenta por cobrar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuenta por cobrar registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroCuentasPorCobrar> registroCuentasPorCobrar(
            @Validated @RequestBody RequestRegistroCuentasPorCobrar request
    ) {
        ResponseRegistroCuentasPorCobrar response = registroCuentasPorCobrarUseCase.RegistroCuentasPorCobrar(request);

        if (response.isExito()) {
            NotificacionCuentasPorCobrarDTO notificacion = new NotificacionCuentasPorCobrarDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nueva cuenta por cobrar registrada");

            notificacionCuentasPorCobrarService.enviarNotificacionCuentasPorCobrar_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar cuenta por cobrar", description = "Permite editar todos los datos de una cuenta por cobrar existente")
    public ResponseEntity<ResponseEditarAllCuentasPorCobrar> edicionAllCuentasPorCobrar(
            @Validated @RequestBody RequestEditarAllCuentasPorCobrar request
    ) {
        ResponseEditarAllCuentasPorCobrar response = edicionAllCuentasPorCobrarUseCase.EdicionAllCuentasPorCobrar(request);

        if (response.isExito()) {
            NotificacionCuentasPorCobrarDTO notificacion = new NotificacionCuentasPorCobrarDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Cuenta por cobrar editada");

            notificacionCuentasPorCobrarService.enviarNotificacionCuentasPorCobrar_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @PatchMapping("/{idCuentaPorCobrar}/cancelar")
    @Operation(summary = "cancelar cuenta por cobrar", description = "Cancela una cuenta por cobrar")
    public ResponseEntity<ResponseEditarEstadoCuentasPorCobrar> CancelarCuentasPorCobrar(
            @PathVariable long idCuentaPorCobrar
    ) {
        ResponseEditarEstadoCuentasPorCobrar response =
                edicionCuentasPorCobrarEstadoUseCase.CancelarCuentasPorCobrar(idCuentaPorCobrar);

        if (response.isExito()) {
            NotificacionCuentasPorCobrarDTO notificacion = new NotificacionCuentasPorCobrarDTO();
            notificacion.setTipo(String.valueOf("Cancelación"));
            notificacion.setMensaje("Cuenta por cobrar Cancelada");
            notificacion.setIdCuentaPorCobrar(idCuentaPorCobrar);

            notificacionCuentasPorCobrarService.enviarNotificacionCuentasPorCobrar_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idCuentaPorCobrar}")
    @Operation(summary = "Detalle cuenta por cobrar", description = "Obtiene el detalle de una cuenta por cobrar")
    public ResponseEntity<ResponseDetalleCuentasPorCobrar> detalleCuentasPorCobrar(
            @PathVariable long idCuentaPorCobrar
    ) {
        ResponseDetalleCuentasPorCobrar response =
                detalleCuentasPorCobrarUseCase.DetalleCuentasPorCobrar(idCuentaPorCobrar);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
