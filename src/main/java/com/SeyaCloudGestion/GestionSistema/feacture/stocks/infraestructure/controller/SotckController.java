package com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.*;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.response.*;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.useCase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/Stock")
public class SotckController {

    @Autowired
    private ListaSotckUseCase listaSotckUseCase;

    @Autowired
    private DetalleSotckUseCase detalleSotckUseCase;

    @GetMapping
    @Operation(summary = "Listar stocks", description = "Obtiene la lista de los stocks registrados")
    public ResponseEntity<ResponseListaSotck> listaSotck(@Validated @ModelAttribute RequestListaSotck request) {

        ResponseListaSotck response = listaSotckUseCase.ListaSotck(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detalle")
    @Operation(summary = "Obtener detalle de stock por artículo y almacén", description = "Obtiene el stock específico de un artículo en un almacén determinado")
    public ResponseEntity<ResponseDetalleSotck> detalleSotck(
            @RequestParam long idArticulo,
            @RequestParam long idAlmacen) {

        ResponseDetalleSotck response = detalleSotckUseCase.DetalleSotck(idArticulo, idAlmacen);

        if (response.isExito() && response.getSotck() != null) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}