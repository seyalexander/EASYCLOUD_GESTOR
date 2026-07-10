package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request.RequestListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseDetalleSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response.ResponseListaSerieCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.useCase.DetalleSerieCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.useCase.ListaSerieCajaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/serieCaja")
public class SerieCajaController {

    private final ListaSerieCajaUseCase listaSerieCajaUseCase;
    private final DetalleSerieCajaUseCase detalleSerieCajaUseCase;

    // Inyección explícita por constructor
    public SerieCajaController(
            ListaSerieCajaUseCase listaSerieCajaUseCase,
            DetalleSerieCajaUseCase detalleSerieCajaUseCase
    ) {
        this.listaSerieCajaUseCase = listaSerieCajaUseCase;
        this.detalleSerieCajaUseCase = detalleSerieCajaUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar series de caja", description = "Obtiene la lista de series asignadas a una caja específica")
    public ResponseEntity<ResponseListaSerieCaja> listaSerieCaja(@Validated @ModelAttribute RequestListaSerieCaja request) {

        ResponseListaSerieCaja response = listaSerieCajaUseCase.ListaSerieCaja(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idSerieCaja}")
    @Operation(summary = "Detalle de serie de caja", description = "Obtiene la información detallada de una serie de caja específica")
    public ResponseEntity<ResponseDetalleSerieCaja> detalleSerieCaja(@PathVariable long idSerieCaja) {

        ResponseDetalleSerieCaja response = detalleSerieCajaUseCase.DetalleSerieCaja(idSerieCaja);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}