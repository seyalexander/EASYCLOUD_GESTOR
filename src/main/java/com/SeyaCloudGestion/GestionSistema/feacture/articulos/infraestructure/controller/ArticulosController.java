package com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase.RegistroArticuloUseCase;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionArticuloDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionArticuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/articulos")
public class ArticulosController {

    @Autowired
    private RegistroArticuloUseCase registroArticuloUseCase;

    @Autowired
    private NotificacionArticuloService notificacionArticuloService;

    @PostMapping
    @Operation(summary = "Registrar artículo", description = "Permite registrar un nuevo artículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artículo registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroArticulo> registroArticulo(
            @Validated @RequestBody RequestRegistroArticulo request) {

        ResponseRegistroArticulo response = registroArticuloUseCase.RegistrarArticulo(request);

        if (response.isExito()) {
            NotificacionArticuloDTO notificacion = new NotificacionArticuloDTO();
            notificacion.setTipo("REGISTRO");
            notificacion.setMensaje("Nuevo artículo registrado");

            notificacionArticuloService.enviarNotificacionArticulo_Registro(notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
