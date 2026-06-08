package com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarAllEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestListaEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestRegistroEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionEmpresaDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/empresa")
public class EmpresasController {

    @Autowired
    private ListaEmpresaUseCase listaEmpresaUseCase;
    @Autowired
    private RegistroEmpresaUseCase registroEmpresaUseCase;
    @Autowired
    private EditarAllEmpresaUseCase editarAllEmpresaUseCase;
    @Autowired
    private EditarEstadoEmpresaUseCase editarEstadoEmpresaUseCase;
    @Autowired
    private DetalleEmpresaUseCase detalleEmpresaUseCase;
    @Autowired
    private NotificacionEmpresaService notificacionEmpresaService;

    @GetMapping
    @Operation(summary = "Listar empresas by estado", description = "Obtiene la lista de empresas según su estado")
    public ResponseEntity<ResponseListaEmpresa> listaEmpresas(@Validated @ModelAttribute RequestListaEmpresa request) {
        ResponseListaEmpresa response = listaEmpresaUseCase.ListarEmpresa(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar empresa", description = "Permite registrar una nueva empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroEmpresa> registroEmpresa(
            @Validated @RequestBody RequestRegistroEmpresa request) {

        ResponseRegistroEmpresa response = registroEmpresaUseCase.RegistroEmpresa(request);

        if (response.isExito()) {
            NotificacionEmpresaDTO notificacion = new NotificacionEmpresaDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nueva empresa registrada");

            notificacionEmpresaService.enviarNotificacionEmpresa_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @PutMapping
    @Operation(summary = "Editar empresa", description = "Permite editar todos los datos de una empresa existente")
    public ResponseEntity<ResponseEditarAllEmpresa> edicionAllEmpresa(
            @Validated @RequestBody RequestEditarAllEmpresa request) {

        ResponseEditarAllEmpresa response = editarAllEmpresaUseCase.EditarEmpresa(request);

        if (response.isExito()) {
            NotificacionEmpresaDTO notificacion = new NotificacionEmpresaDTO();
            notificacion.setTipo("EDICION");
            notificacion.setMensaje("Empresa editada");

            notificacionEmpresaService.enviarNotificacionEmpresa_Edicion(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idEmpresa}")
    @Operation(summary = "Anular Empresa", description = "Cambia el estado de una empresa a inactivo")
    public ResponseEntity<ResponseEditarEstadoEmpresa> anularEmpresa(@PathVariable long idEmpresa) {

        ResponseEditarEstadoEmpresa response = editarEstadoEmpresaUseCase.AnularEmpresa(idEmpresa);

        if (response.isExito()) {
            NotificacionEmpresaDTO notificacion = new NotificacionEmpresaDTO();
            notificacion.setTipo("ANULACION");
            notificacion.setMensaje("Empresa anulada");
            notificacion.setIdEmpresa(idEmpresa);

            notificacionEmpresaService.enviarNotificacionEmpresa_Anular(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{idEmpresa}/activar")
    @Operation(summary = "Activar empresa by id", description = "Activa nuevamente un tipo de documento previamente anulado")
    public ResponseEntity<ResponseEditarEstadoEmpresa> activarEmpresa(@PathVariable long idEmpresa) {

        ResponseEditarEstadoEmpresa response = editarEstadoEmpresaUseCase.ActivarEmpresa(idEmpresa);

        if (response.isExito()) {
            NotificacionEmpresaDTO notificacion = new NotificacionEmpresaDTO();
            notificacion.setTipo("ACTIVACIÓN");
            notificacion.setMensaje("Empresa anulada");
            notificacion.setIdEmpresa(idEmpresa);

            notificacionEmpresaService.enviarNotificacionEmpresa_Activar(notificacion);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idEmpresa}")
    @Operation(summary = "Detalle empresa", description = "Obtiene el detalle de una empresa")
    public ResponseEntity<ResponseDetalleEmpresa> detalleEmpresa(@PathVariable long idEmpresa) {

        ResponseDetalleEmpresa response = detalleEmpresaUseCase.DetalleEmpresa(idEmpresa);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
