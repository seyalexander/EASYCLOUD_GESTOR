package com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request.RequestListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseDetalleKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response.ResponseListaKardex;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.DetalleKardexUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.useCase.ListaKardexUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/Kardex")
public class KardexController {

    private final ListaKardexUseCase listaKardexUseCase;
    private final DetalleKardexUseCase detalleKardexUseCase;

    public KardexController(ListaKardexUseCase listaKardexUseCase, DetalleKardexUseCase detalleKardexUseCase) {
        this.listaKardexUseCase = listaKardexUseCase;
        this.detalleKardexUseCase = detalleKardexUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar movimientos de Kardex", description = "Obtiene el historial de movimientos de Kardex filtrado por artículo y rango de fechas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en las fechas o parámetros enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseListaKardex> listaKardex(@Validated @ModelAttribute RequestListaKardex request) {

        ResponseListaKardex response = listaKardexUseCase.listaKardex(request);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    /*
    @GetMapping("/{idKardex}")
    @Operation(summary = "Obtener detalle de Kardex", description = "Obtiene los datos completos y auditoría de un único movimiento de Kardex por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle del movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimiento de Kardex no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseDetalleKardex> detalleKardex(@PathVariable long idKardex) {

        ResponseDetalleKardex response = detalleKardexUseCase.detalleKardex(idKardex);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

     */
}