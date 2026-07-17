package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.useCase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/inventario")
public class InventarioController {

    @Autowired
    private ListaInventarioUseCase listaInventarioUseCase;

    @Autowired
    private DetalleInventarioUseCase detalleInventarioUseCase;

    @Autowired
    private RegistroInventarioUseCase registroInventarioUseCase;

    @Autowired
    private AjustarInventarioUseCase ajustarInventarioUseCase;

    @Autowired
    private ConteoFisicoInventarioUseCase conteoFisicoInventarioUseCase;

    @GetMapping
    @Operation(summary = "Listar inventarios", description = "Obtiene la lista de inventarios bajo ciertos filtros")
    public ResponseEntity<ResponseListaInventario> listarInventario(
            @Validated @ModelAttribute RequestListaInventario request) {

        ResponseListaInventario response = listaInventarioUseCase.ListaInventario(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idInventario}")
    @Operation(summary = "Obtener detalle del inventario", description = "Retorna el detalle de un inventario específico por su ID")
    public ResponseEntity<ResponseDetalleInventario> detalleInventario(
            @PathVariable long idInventario) {

        ResponseDetalleInventario response = detalleInventarioUseCase.DetalleInventario(idInventario);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping
    @Operation(
            summary = "1. Aperturar Inventario (Planificación)",
            description = "Inicia un proceso de toma de inventario registrando la lista de artículos que serán auditados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario aperturado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos de entrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroInventario> registroInventario(
            @Validated @RequestBody RequestRegistroInventario request) {

        ResponseRegistroInventario response = registroInventarioUseCase.RegistroInventario(request);

        if (response.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/conteo-fisico")
    @Operation(
            summary = "2. Registrar Conteo Físico",
            description = "Ingresa las cantidades reales contadas físicamente en el almacén. El sistema calcula automáticamente las diferencias (discrepancias) entre lo teórico y lo físico."
    )
    public ResponseEntity<ResponseConteoFisicoInventario> conteoFisicoInventario(
            @Validated @RequestBody RequestConteoFisicoInventario request) {

        ResponseConteoFisicoInventario response = conteoFisicoInventarioUseCase.ConteoFisicoInventario(request);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/ajustar")
    @Operation(
            summary = "3. Aplicar Ajustes en Stock y Kardex (Cierre)",
            description = "Finaliza el proceso aplicando las correcciones de inventario. Enviando solo el ID del inventario, el sistema procesa de forma automática los ingresos o egresos requeridos tanto en Stock como en Kardex."
    )
    public ResponseEntity<ResponseAjustarInventario> ajustarInventario(
            @Validated @RequestBody RequestAjustarInventario request) {

        ResponseAjustarInventario response = ajustarInventarioUseCase.AjustarInventario(request);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
