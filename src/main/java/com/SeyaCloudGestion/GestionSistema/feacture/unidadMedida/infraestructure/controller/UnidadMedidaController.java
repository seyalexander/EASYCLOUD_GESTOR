package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestEditarAllUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestRegistroUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionUnidadMedidaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionUnidadMedidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/unidadMedida")
public class UnidadMedidaController {

    @Autowired
    private ListaUnidadMedidaUseCase listaUnidadMedidaUseCase;

    @Autowired
    private DetalleUnidadMedidaUseCase detalleUnidadMedidaUseCase;

    @Autowired
    private EdicionUnidadMedidaEstadoUseCase edicionUnidadMedidaEstadoUseCase;

    @Autowired
    private EdicionUnidadMedidaUseCase edicionUnidadMedidaUseCase;

    @Autowired
    private RegistroUnidadMedidaUseCase registroUnidadMedidaUseCase;

    @Autowired
    private NotificacionUnidadMedidaService notificacionUnidadMedidaService;

    @GetMapping
    @Operation(summary = "Listar unidad de medida by estado", description = "Obtiene la lista de unidad de medida según su estado")
    public ResponseEntity<ResponseListaUnidadMedida> listaUnidadMedida(@Validated @ModelAttribute RequestListaUnidadMedida request) {
        ResponseListaUnidadMedida response = listaUnidadMedidaUseCase.listaUnidadMedida(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    @Operation(
            summary = "Registrar unidad de medida",
            description = "Permite registrar una nueva unidad de medida"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidad de medida registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroUnidadMedida> registroUnidadMedida(
            @Validated @RequestBody RequestRegistroUnidadMedida request) {

        ResponseRegistroUnidadMedida response = registroUnidadMedidaUseCase.RegistroUnidadMedida(request);

        if (response.isExito()) {
            NotificacionUnidadMedidaDTO notificacion = new NotificacionUnidadMedidaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nueva unidad de medida registrada");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar unidad de medida", description = "Permite editar todos los datos de una unidad de medida existente")
    public ResponseEntity<ResponseEditarAllUnidadMedida> edicionAllUnidadMedida(@Validated @RequestBody RequestEditarAllUnidadMedida request) {

        ResponseEditarAllUnidadMedida response = edicionUnidadMedidaUseCase.EdicionAllUnidadMedida(request);

        if (response.isExito()) {
            NotificacionUnidadMedidaDTO notificacion = new NotificacionUnidadMedidaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Unidad de medida editada");
            notificacion.setIdUnidadMedida(request.getIdUnidadMedida());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idUnidadMedida}")
    @Operation(
            summary = "Anular unidad de medida",
            description = "Cambia el estado de la unidad de medida a inactivo"
    )
    public ResponseEntity<ResponseEditarEstadoUnidadMedida> anularUnidadMedida(@PathVariable long idUnidadMedida) {

        ResponseEditarEstadoUnidadMedida response = edicionUnidadMedidaEstadoUseCase.AnularUnidadMedida(idUnidadMedida);

        if (response.isExito()) {
            NotificacionUnidadMedidaDTO notificacion = new NotificacionUnidadMedidaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Unidad de medida anulada");
            notificacion.setIdUnidadMedida(idUnidadMedida);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idUnidadMedida}/activar")
    @Operation(summary = "Activar unidad de medida por id", description = "Activa nuevamente una unidad de medida previamente anulada")
    public ResponseEntity<ResponseEditarEstadoUnidadMedida> activarUnidadMedida(@PathVariable long idUnidadMedida) {

        ResponseEditarEstadoUnidadMedida response = edicionUnidadMedidaEstadoUseCase.ActivarUnidadMedida(idUnidadMedida);

        if (response.isExito()) {
            NotificacionUnidadMedidaDTO notificacion = new NotificacionUnidadMedidaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Unidad de medida activada");
            notificacion.setIdUnidadMedida(idUnidadMedida);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idUnidadMedida}")
    @Operation(summary = "Detalle unidad de medida", description = "Obtiene el detalle de una unidad de medida")
    public ResponseEntity<ResponseDetalleUnidadMedida> detalleUnidadMedida(@PathVariable long idUnidadMedida) {

        ResponseDetalleUnidadMedida response = detalleUnidadMedidaUseCase.DetalleUnidadMedida(idUnidadMedida);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
