package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.useCase.ListaDetalleInventarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/detalle-inventario")
public class DetalleInventarioController {

    @Autowired
    private ListaDetalleInventarioUseCase listaDetalleInventarioUseCase;

    @GetMapping
    @Operation(
            summary = "Listar detalles de inventario",
            description = "Obtiene la lista de los detalles de inventario registrados"
    )
    public ResponseEntity<ResponseListaDetalleInventario> listaDetalleInventario(
            @Validated @RequestParam long idCabezeraIventario,long idAlmacen) {

        ResponseListaDetalleInventario response = listaDetalleInventarioUseCase.listarDetalleInventario( idCabezeraIventario, idAlmacen);

        return ResponseEntity.ok(response);
    }
}