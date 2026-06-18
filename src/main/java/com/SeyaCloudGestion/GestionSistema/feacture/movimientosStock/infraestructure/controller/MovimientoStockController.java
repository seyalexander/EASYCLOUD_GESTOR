package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request.RequestListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseDetalleMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.response.ResponseListaMovimientoStock;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase.DetalleMovimientoStockUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.useCase.ListaMovimientoStockUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/movimiento-stock")
public class MovimientoStockController {

    private final ListaMovimientoStockUseCase listaMovimientoStockUseCase;
    private final DetalleMovimientoStockUseCase detalleMovimientoStockUseCase;

    public MovimientoStockController(
            ListaMovimientoStockUseCase listaMovimientoStockUseCase,
            DetalleMovimientoStockUseCase detalleMovimientoStockUseCase
    ) {
        this.listaMovimientoStockUseCase = listaMovimientoStockUseCase;
        this.detalleMovimientoStockUseCase = detalleMovimientoStockUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar movimientos de stock", description = "Obtiene la lista de movimientos filtrando obligatoriamente por empresa, sucursal y almacén")
    public ResponseEntity<ResponseListaMovimientoStock> listaMovimientoStock(@Validated @ModelAttribute RequestListaMovimientoStock request) {

        ResponseListaMovimientoStock response = listaMovimientoStockUseCase.ListaMovimientoStock(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idMovimientoStock}")
    @Operation(summary = "Detalle de movimiento de stock", description = "Obtiene el detalle de un movimiento específico validando su jerarquía")
    public ResponseEntity<ResponseDetalleMovimientoStock> detalleMovimientoStock(
            @PathVariable long idMovimientoStock) {

        ResponseDetalleMovimientoStock response = detalleMovimientoStockUseCase.DetalleMovimientoStock(idMovimientoStock);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}