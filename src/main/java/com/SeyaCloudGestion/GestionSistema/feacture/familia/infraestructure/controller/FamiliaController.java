package com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionFamiliaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionFamiliaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/familia")
public class FamiliaController {

    @Autowired
    private ListaFamiliaUseCase listaFamiliaUseCase;

    @Autowired
    private RegistroFamiliaUseCase registroFamiliaUseCase;

    @Autowired
    private EdicionFamiliaUseCase edicionFamiliaUseCase;

    @Autowired
    private EdicionFamiliaEstadoUseCase edicionFamiliaEstadoUseCase;

    @Autowired
    private DetalleFamiliaUseCase detalleFamiliaUseCase;

    @Autowired
    private NotificacionFamiliaService notificacionFamiliaService;

    @GetMapping
    @Operation(summary = "Listar familias by estado", description = "Obtiene la lista de familias según su estado")
    public ResponseEntity<ResponseListaFamilia> listaFamilia(@Validated @ModelAttribute RequestListaFamilia request) {

        ResponseListaFamilia response = listaFamiliaUseCase.ListaFamilia(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar familia", description = "Permite registrar una nueva familia de productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Familia registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroFamilia> registroFamilia(
            @Validated @RequestBody RequestRegistroFamilia request) {

        ResponseRegistroFamilia response = registroFamiliaUseCase.RegistroFamilia(request);

        if (response.isExito()) {
            NotificacionFamiliaDTO notificacion = new NotificacionFamiliaDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nueva familia registrada");

            notificacionFamiliaService.enviarNotificacionFamilia_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar familia", description = "Permite editar todos los datos de una familia existente")
    public ResponseEntity<ResponseEditarAllFamilia> edicionAllFamilia(
            @Validated @RequestBody RequestEditarAllFamilia request) {

        ResponseEditarAllFamilia response = edicionFamiliaUseCase.EdicionAllFamilia(request);

        if (response.isExito()) {
            NotificacionFamiliaDTO notificacion = new NotificacionFamiliaDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Familia editada");

            notificacionFamiliaService.enviarNotificacionFamilia_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idFamilia}")
    @Operation(summary = "Anular familia", description = "Cambia el estado de la familia a inactivo")
    public ResponseEntity<ResponseEditarEstadoFamilia> anularFamilia(@PathVariable long idFamilia) {

        ResponseEditarEstadoFamilia response = edicionFamiliaEstadoUseCase.EdicionAnularFamilia(idFamilia);

        if (response.isExito()) {
            NotificacionFamiliaDTO notificacion = new NotificacionFamiliaDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Familia anulada");
            notificacion.setIdFamilia(idFamilia);

            notificacionFamiliaService.enviarNotificacionFamilia_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idFamilia}/activar")
    @Operation(summary = "Activar familia by id", description = "Activa nuevamente una familia previamente anulada")
    public ResponseEntity<ResponseEditarEstadoFamilia> activarFamilia(@PathVariable long idFamilia) {

        ResponseEditarEstadoFamilia response = edicionFamiliaEstadoUseCase.EdicionActivarFamilia(idFamilia);

        if (response.isExito()) {
            NotificacionFamiliaDTO notificacion = new NotificacionFamiliaDTO();
            notificacion.setTipo("ACTIVACION");
            notificacion.setMensaje("Familia activada");
            notificacion.setIdFamilia(idFamilia);

            notificacionFamiliaService.enviarNotificacionFamilia_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idFamilia}")
    @Operation(summary = "Detalle familia", description = "Obtiene el detalle de una familia de productos")
    public ResponseEntity<ResponseDetalleFamilia> detalleFamilia(@PathVariable long idFamilia) {

        ResponseDetalleFamilia response = detalleFamiliaUseCase.DetalleFamilia(idFamilia);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
