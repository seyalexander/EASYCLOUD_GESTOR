package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.infrastructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.useCase.ListaDetalleCompraUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/detalle-compra")
public class DetalleCompraController {

    @Autowired
    private ListaDetalleCompraUseCase listaDetalleCompraUseCase;

    @GetMapping
    @Operation(
            summary = "Listar detalles de compra",
            description = "Obtiene la lista de los detalles de compra registrados"
    )
    public ResponseEntity<ResponseListaDetalleCompra> listaDetalleCompra(
            @Validated @RequestParam long idCompra) {

        ResponseListaDetalleCompra response = listaDetalleCompraUseCase.listarDetalleCompra(idCompra);

        return ResponseEntity.ok(response);
    }
}