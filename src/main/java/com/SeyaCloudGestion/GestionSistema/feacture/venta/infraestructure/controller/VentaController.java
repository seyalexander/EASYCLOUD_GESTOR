package com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.useCase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/Venta")
public class VentaController {

    private final ListaVentaUseCase listaVentaUseCase;
    private final RegistroVentaUseCase registroVentaUseCase;
    private final EdicionVentaEstadoUseCase edicionVentaEstadoUseCase;
    private final DetalleVentaUseCase detalleVentaUseCase;

    // 🌟 INYECCIÓN EXPLÍCITA POR CONSTRUCTOR
    public VentaController(
            ListaVentaUseCase listaVentaUseCase,
            RegistroVentaUseCase registroVentaUseCase,
            EdicionVentaEstadoUseCase edicionVentaEstadoUseCase,
            DetalleVentaUseCase detalleVentaUseCase
    ) {
        this.listaVentaUseCase = listaVentaUseCase;
        this.registroVentaUseCase = registroVentaUseCase;
        this.edicionVentaEstadoUseCase = edicionVentaEstadoUseCase;
        this.detalleVentaUseCase = detalleVentaUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar ventas by estado", description = "Obtiene la lista de ventas según los filtros y estado")
    public ResponseEntity<ResponseListaVenta> listaVenta(@Validated @ModelAttribute RequestListaVenta request) {
        ResponseListaVenta response = listaVentaUseCase.listaVenta(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar venta", description = "Permite registrar una nueva venta junto a todos sus detalles y procesar inventarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta y movimientos de stock procesados correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados o stock insuficiente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroVenta> registroVenta(
            @Validated @RequestBody RequestRegistroVenta request) {

        ResponseRegistroVenta response = registroVentaUseCase.RegistroVenta(request);

        if (response.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{idVenta}")
    @Operation(summary = "Anular venta", description = "Cambia el estado de la venta a anulado (0)")
    public ResponseEntity<ResponseEditarEstadoVenta> anularVenta(@PathVariable long idVenta) {

        ResponseEditarEstadoVenta response = edicionVentaEstadoUseCase.EdicionAnularVenta(idVenta);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idVenta}")
    @Operation(summary = "Detalle de una venta", description = "Obtiene los datos de cabecera y lista de artículos vinculados a una venta")
    public ResponseEntity<ResponseDetalleVenta> detalleVenta(@PathVariable long idVenta) {

        ResponseDetalleVenta response = detalleVentaUseCase.DetalleVenta(idVenta);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}