package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.infrastructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestListaDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseListaDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.useCase.ListaDetalleVentaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/detalle-venta")
public class DetalleVentaController {

    @Autowired
    private ListaDetalleVentaUseCase listaDetalleVentaUseCase;

    @GetMapping
    @Operation(
            summary = "Listar detalles de venta",
            description = "Obtiene la lista de los detalles de venta registrados"
    )
    public ResponseEntity<ResponseListaDetalleVenta> listaDetalleVenta(
            @Validated @RequestParam long idVenta) {

        ResponseListaDetalleVenta response = listaDetalleVentaUseCase.listarDetalleVenta(idVenta);

        return ResponseEntity.ok(response);
    }
}