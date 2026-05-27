package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestEditarAllTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestListaTipoDocumentos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestRegistroTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionTipoDocumentoDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionTipoDocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tipoDocumento")
public class TipoDocumentoController {

    @Autowired
    private ListaTipoDocumentoUseCase listaTipoDocumentoUseCase;
    @Autowired
    private RegistroTipoDocumentoUseCase  registroTipoDocumentoUseCase;
    @Autowired
    private NotificacionTipoDocumentoService notificacionTipoDocumentoService;
    @Autowired
    private EdicionAllTipoDocumentoUseCase edicionAllTipoDocumentoUseCase;
    @Autowired
    private EdicionEstadoTipoDocumentoUseCase  edicionEstadoTipoDocumentoUseCase;
    @Autowired
    private DetalleTipoDocumentoUseCase detalleTipoDocumentoUseCase;

    @GetMapping
    @Operation(summary = "Listar tipo documentos by estado", description = "Obtiene la lista de tipo documentos según su estado")
    public ResponseEntity<ResponseListaTipoDocumento> listaTipoDocumento(@Validated @ModelAttribute RequestListaTipoDocumentos request) {
        ResponseListaTipoDocumento response = listaTipoDocumentoUseCase.ListarTipoDocumento(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar Tipo documento", description = "Permite registrar un nuevo tipo de documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo documento registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroTipoDocumento> registroTipoDocumento(
            @Validated @RequestBody RequestRegistroTipoDocumento request) {

        ResponseRegistroTipoDocumento response = registroTipoDocumentoUseCase.RegistroTipoDocumento(request);

        if (response.isExito()) {
            NotificacionTipoDocumentoDTO notificacion = new NotificacionTipoDocumentoDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nuevo tipo documento registrado");

            notificacionTipoDocumentoService.enviarNotificacionTipoDocumento_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar Tipo documento", description = "Permite editar todos los datos de un Tipo documento existente")
    public ResponseEntity<ResponseEditarAllTipoDocumento> edicionAllTipoDocumento(
            @Validated @RequestBody RequestEditarAllTipoDocumento request) {

        ResponseEditarAllTipoDocumento response = edicionAllTipoDocumentoUseCase.EditarAllTipoDocumento(request);

        if (response.isExito()) {
            NotificacionTipoDocumentoDTO notificacion = new NotificacionTipoDocumentoDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Tipo Documento editado");

            notificacionTipoDocumentoService.enviarNotificacionTipoDocument_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idTipoDocumentos}")
    @Operation(summary = "Anular Tipo documento", description = "Cambia el estado de un tipo documento a inactivo")
    public ResponseEntity<ResponseEditarEstadoTipoDocumento> anularTipoDocumento(@PathVariable long idTipoDocumentos) {

        ResponseEditarEstadoTipoDocumento response = edicionEstadoTipoDocumentoUseCase.AnularTipoDocumento(idTipoDocumentos);

        if (response.isExito()) {
            NotificacionTipoDocumentoDTO notificacion = new NotificacionTipoDocumentoDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Tipo Documento anulado");
            notificacion.setIdTipoDocumentos(idTipoDocumentos);

            notificacionTipoDocumentoService.enviarNotificacionTipoDocument_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idTipoDocumentos}/activar")
    @Operation(summary = "Activar tipo documento by id", description = "Activa nuevamente un tipo de documento previamente anulado")
    public ResponseEntity<ResponseEditarEstadoTipoDocumento> activarTipoDocumento(@PathVariable long idTipoDocumentos) {

        ResponseEditarEstadoTipoDocumento response = edicionEstadoTipoDocumentoUseCase.ActivarTipoDocumento(idTipoDocumentos);

        if (response.isExito()) {
            NotificacionTipoDocumentoDTO notificacion = new NotificacionTipoDocumentoDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.ACTIVACION));
            notificacion.setMensaje("Tipo Documento activado");
            notificacion.setIdTipoDocumentos(idTipoDocumentos);

            notificacionTipoDocumentoService.enviarNotificacionTipoDocument_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idTipoDocumento}")
    @Operation(summary = "Detalle Empleado", description = "Obtiene el detalle de un empleado")
    public ResponseEntity<ResponseDetalleTipoDocumento> detalleEmpleado(@PathVariable long idTipoDocumento) {

        ResponseDetalleTipoDocumento response = detalleTipoDocumentoUseCase.DetalleTipoDocumento(idTipoDocumento);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
