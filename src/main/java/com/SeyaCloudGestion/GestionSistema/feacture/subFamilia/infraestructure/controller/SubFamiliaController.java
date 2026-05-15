package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseDetalleFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response.ResponseEditarEstadoFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestListaSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestRegistrarSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionFamiliaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionSubFamiliaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionSubFamiliaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/subfamilia")
public class SubFamiliaController {

    @Autowired
    private ListaSubFamiliaUseCase listaSubFamiliaUseCase;

    @Autowired
    private RegistroSubFamiliaUseCase registroSubFamiliaUseCase;

    @Autowired
    private EdicionSubFamiliaAllUseCase edicionSubFamiliaAllUseCase;

    @Autowired
    private NotificacionSubFamiliaService notificacionSubFamiliaService;

    @Autowired
    private DetalleSubFamiliaUseCase detalleSubFamiliaUseCase;

    @Autowired
    private EdicionSubFamiliaEstadoUseCase edicionSubFamiliaEstadoUseCase;

    @GetMapping
    @Operation(summary = "Listar subfamilias by estado", description = "Obtiene la lista de subfamilias según su estado")
    public ResponseEntity<ResponseListaSubFamilia> listaSubFamilia(@Validated @ModelAttribute RequestListaSubFamilia request) {

        ResponseListaSubFamilia response = listaSubFamiliaUseCase.ListaSubFamilia(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar sub familia", description = "Permite registrar una nueva sub familia de productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Familia registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroSubFamilia> registroSubFamilia(
            @Validated @RequestBody RequestRegistrarSubFamilia request) {

        ResponseRegistroSubFamilia response = registroSubFamiliaUseCase.RegistroSubFamilia(request);

        if (response.isExito()) {
            NotificacionSubFamiliaDTO notificacion = new NotificacionSubFamiliaDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nueva subfamilia registrada");

            notificacionSubFamiliaService.enviarNotificacionSubFamilia_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar sub familia", description = "Permite editar todos los datos de una subfamilia existente")
    public ResponseEntity<ResponseEditarAllSubFamilia> edicionAllSubFamilia(
            @Validated @RequestBody RequestEditarAllSubFamilia request) {

        ResponseEditarAllSubFamilia response = edicionSubFamiliaAllUseCase.EdicionAllFamilia(request);

        if (response.isExito()) {
            NotificacionSubFamiliaDTO notificacion = new NotificacionSubFamiliaDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Subfamilia editada");

            notificacionSubFamiliaService.enviarNotificacionSubFamilia_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idSubFamilia}")
    @Operation(summary = "Anular sub familia", description = "Cambia el estado de la familia a inactivo")
    public ResponseEntity<ResponseEditarEstadoSubFamilia> anularFamilia(@PathVariable long idSubFamilia) {

        ResponseEditarEstadoSubFamilia response = edicionSubFamiliaEstadoUseCase.AnularSubFamilia(idSubFamilia);

        if (response.isExito()) {
            NotificacionSubFamiliaDTO notificacion = new NotificacionSubFamiliaDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Sub Familia anulada");
            notificacion.setIdFamilia(idSubFamilia);

            notificacionSubFamiliaService.enviarNotificacionSubFamilia_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idSubFamilia}/activar")
    @Operation(summary = "Activar familia by id", description = "Activa nuevamente una familia previamente anulada")
    public ResponseEntity<ResponseEditarEstadoSubFamilia> activarFamilia(@PathVariable long idSubFamilia) {

        ResponseEditarEstadoSubFamilia response = edicionSubFamiliaEstadoUseCase.ActivarSubFamilia(idSubFamilia);

        if (response.isExito()) {
            NotificacionSubFamiliaDTO notificacion = new NotificacionSubFamiliaDTO();
            notificacion.setTipo("ACTIVACION");
            notificacion.setMensaje("Sub Familia activada");
            notificacion.setIdFamilia(idSubFamilia);

            notificacionSubFamiliaService.enviarNotificacionSubFamilia_Activar(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idSubFamilia}")
    @Operation(summary = "Detalle sub familia", description = "Obtiene el detalle de una sub familia de productos")
    public ResponseEntity<ResponseDetalleSubFamilia> detalleSubFamilia(@PathVariable long idSubFamilia) {

        ResponseDetalleSubFamilia response = detalleSubFamiliaUseCase.DetalleSubFamilia(idSubFamilia);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
