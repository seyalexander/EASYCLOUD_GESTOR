package com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionMarcaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionMarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/marca")
public class MarcaController {

    @Autowired
    private ListaMarcaUseCase listaMarcaUseCase;

    @Autowired
    private DetalleMarcaUseCase detalleMarcaUseCase;

    @Autowired
    private EdicionMarcaUseCase edicionMarcaUseCase;

    @Autowired
    private RegistroMarcaUseCase registroMarcaUseCase;

    @Autowired
    private EdicionMarcaEstadoUseCase edicionMarcaEstadoUseCase;

    @Autowired
    private NotificacionMarcaService notificacionMarcaService;

    @GetMapping
    @Operation(summary = "Listar marca by estado", description = "Obtiene la lista de marcas según su estado")
    public ResponseEntity<ResponseListaMarca> listaMarcas(@Validated @ModelAttribute RequestListaMarca request) {
        ResponseListaMarca response = listaMarcaUseCase.listaMarcas(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    @Operation(summary = "Registrar marca", description = "Permite registrar una nueva marca de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroMarca> registroMarca(
            @Validated @RequestBody RequestRegistroMarca request) {

        ResponseRegistroMarca response = registroMarcaUseCase.RegistroMarca(request);

        if (response.isExito()) {
            NotificacionMarcaDTO notificacion = new NotificacionMarcaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nueva marca registrada");

            notificacionMarcaService.enviarNotificacionMarca_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar marca", description = "Permite editar todos los datos de una marca existente")
    public ResponseEntity<ResponseEditarAllMarca> edicionAllMarca(
            @Validated @RequestBody RequestEditarAllMarca request) {

        ResponseEditarAllMarca response = edicionMarcaUseCase.EdicionAllMarca(request);

        if (response.isExito()) {
            NotificacionMarcaDTO notificacion = new NotificacionMarcaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Subfamilia editada");
            notificacionMarcaService.enviarNotificacionMarca_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idMarca}")
    @Operation(summary = "Anular marca", description = "Cambia el estado de la marca a inactivo")
    public ResponseEntity<ResponseEditarEstadoMarca> anularMarca(@PathVariable long idMarca) {
        ResponseEditarEstadoMarca response = edicionMarcaEstadoUseCase.AnularMarca(idMarca);

        if (response.isExito()) {
            NotificacionMarcaDTO notificacion = new NotificacionMarcaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ANULACION));
            notificacion.setMensaje("Marca Anulada");
            notificacion.setIdMarca(idMarca);

            notificacionMarcaService.enviarNotificacionMarca_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idMarca}/activar")
    @Operation(summary = "Activar marca by id", description = "Activa nuevamente una marca previamente anulada")
    public ResponseEntity<ResponseEditarEstadoMarca> activarMarca(@PathVariable long idMarca) {

        ResponseEditarEstadoMarca response = edicionMarcaEstadoUseCase.ActivarMarca(idMarca);

        if (response.isExito()) {
            NotificacionMarcaDTO notificacion = new NotificacionMarcaDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Marca activada");
            notificacion.setIdMarca(idMarca);

            notificacionMarcaService.enviarNotificacionMarca_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idMarca}")
    @Operation(summary = "Detalle marca", description = "Obtiene el detalle de una marca de productos")
    public ResponseEntity<ResponseDetalleMarca> detalleMarca(@PathVariable long idMarca) {
        ResponseDetalleMarca response = detalleMarcaUseCase.detalleMarcas(idMarca);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
