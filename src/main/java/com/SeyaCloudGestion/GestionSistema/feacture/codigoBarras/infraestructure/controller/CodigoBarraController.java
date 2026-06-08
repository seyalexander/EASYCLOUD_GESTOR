package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.controller;
import com.SeyaCloudGestion.GestionSistema.common.enums.TipoNotificacion;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.useCase.*;
import com.SeyaCloudGestion.GestionSistema.websockets.application.dto.NotificacionCodigoBarraDTO;
import com.SeyaCloudGestion.GestionSistema.websockets.domain.services.NotificacionCodigoBarraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/codigoBarras")
public class CodigoBarraController {
    private final ListaCodigoBarraUseCase listaCodigoBarraUseCase;
    private final RegistroCodigoBarraUseCase registroCodigoBarraUseCase;
    private final EdicionAllCodigoBarraUseCase edicionAllCodigoBarraUseCase;
    private final NotificacionCodigoBarraService notificacionCodigoBarraService;

    public CodigoBarraController(
            ListaCodigoBarraUseCase listaCodigoBarraUseCase,
            RegistroCodigoBarraUseCase registroCodigoBarraUseCase, EdicionAllCodigoBarraUseCase edicionAllCodigoBarraUseCase,
            NotificacionCodigoBarraService notificacionCodigoBarraService
    ) {
        this.listaCodigoBarraUseCase = listaCodigoBarraUseCase;
        this.registroCodigoBarraUseCase = registroCodigoBarraUseCase;
        this.edicionAllCodigoBarraUseCase = edicionAllCodigoBarraUseCase;
        this.notificacionCodigoBarraService = notificacionCodigoBarraService;
    }

    @GetMapping
    @Operation(summary = "Listar códigos de barra", description = "Obtiene la lista de códigos de barra según los filtros enviados")
    public ResponseEntity<ResponseListaCodigoBarra> listaCodigoBarra(
            //@Validated @ModelAttribute RequestListaCodigoBarra request
    ) {
        ResponseListaCodigoBarra response = listaCodigoBarraUseCase.ListaCodigoBarra();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar código de barra", description = "Permite registrar un nuevo código de barra para un artículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Código de barra registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroCodigoBarra> registroCodigoBarra(
            @Validated @RequestBody RequestRegistroCodigoBarra request
    ) {
        ResponseRegistroCodigoBarra response = registroCodigoBarraUseCase.RegistroCodigoBarra(request);

        if (response.isExito()) {
            NotificacionCodigoBarraDTO notificacion = new NotificacionCodigoBarraDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.REGISTRO));
            notificacion.setMensaje("Nuevo código de barra registrado");
            notificacion.setIdArticulo(request.getIdArticulo());
            notificacion.setCodigo(request.getCodigo());

            notificacionCodigoBarraService.enviarNotificacionCodigoBarra_Registro(notificacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    @Operation(summary = "Editar código de barra", description = "Permite editar los datos de un código de barra existente")
    public ResponseEntity<ResponseEditarAllCodigoBarra> edicionAllCodigoBarra(
            @Validated @RequestBody RequestEditarAllCodigoBarra request
    ) {
        ResponseEditarAllCodigoBarra response = edicionAllCodigoBarraUseCase.EdicionAllCodigoBarra(request);

        if (response.isExito()) {
            NotificacionCodigoBarraDTO notificacion = new NotificacionCodigoBarraDTO();
            notificacion.setTipo(String.valueOf(TipoNotificacion.EDICION));
            notificacion.setMensaje("Código de barra editado");
            notificacion.setIdCodigoBarra(request.getIdCodigoBarra());
            notificacion.setIdArticulo(request.getIdArticulo());
            notificacion.setCodigo(request.getCodigo());

            notificacionCodigoBarraService.enviarNotificacionCodigoBarra_Edicion(notificacion);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
