package com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestListaCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseListaCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseRegistroCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.useCase.DetalleCompraUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.useCase.ListaCompraUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.useCase.RegistroCompraUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/compra")
public class CompraController {

    @Autowired
    private ListaCompraUseCase listaCompraUseCase;

    @Autowired
    private RegistroCompraUseCase registroCompraUseCase;

    @Autowired
    private DetalleCompraUseCase detalleCompraUseCase;

    @GetMapping
    @Operation(summary = "Listar compras", description = "Obtiene la lista de compras filtrada por los parámetros enviados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de compras obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseListaCompra> listaCompra(
            @Validated @ModelAttribute RequestListaCompra request) {

        ResponseListaCompra response = listaCompraUseCase.ListaCompra(request);

        return ResponseEntity.ok(response);
    }
    @PostMapping
    @Operation(summary = "Registrar compra", description = "Permite registrar una nueva compra en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos de la compra enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroCompra> registroCompra(
            @Validated @RequestBody RequestRegistroCompra request) {

        ResponseRegistroCompra response = registroCompraUseCase.registroCompra(request);

        if (response.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idCompra}")
    @Operation(summary = "Detalle de compra by ID", description = "Obtiene la información detallada de una compra específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de la compra encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró la compra con el ID proporcionado")
    })
    public ResponseEntity<ResponseDetalleCompra> detalleCompra(@PathVariable long idCompra) {

        ResponseDetalleCompra response = detalleCompraUseCase.detalleCompra(idCompra);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}