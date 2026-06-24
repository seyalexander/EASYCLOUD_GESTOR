package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseDetalleMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseRegistroMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase.DetalleMovimientoCajaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.useCase.RegistroMovimientoCajaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/MovimientoCaja")
public class MovimientoCajaController {


    private final RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase;
    private final DetalleMovimientoCajaUseCase detalleMovimientoCajaUseCase;

    public MovimientoCajaController(RegistroMovimientoCajaUseCase registroMovimientoCajaUseCase, DetalleMovimientoCajaUseCase detalleMovimientoCajaUseCase) {
        this.registroMovimientoCajaUseCase = registroMovimientoCajaUseCase;
        this.detalleMovimientoCajaUseCase = detalleMovimientoCajaUseCase;
    }
    /*
    @PostMapping
    @Operation(summary = "Registrar movimiento de caja", description = "Permite registrar un ingreso o egreso manual de dinero en un turno de caja activo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos de entrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroMovimientoCaja> registroMovimientoCaja(
            @Validated @RequestBody RequestRegistroMovimientoCaja request) {

        ResponseRegistroMovimientoCaja response = registroMovimientoCajaUseCase.registroMovimientoCaja(request);

        if (response.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
     */

    @GetMapping("/{idMovimientoCaja}/turno/{idTurnoCaja}")
    @Operation(summary = "Obtener detalle de un movimiento de caja", description = "Obtiene la información detallada de un movimiento específico mediante su ID y el ID del turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimiento de caja no encontrado")
    })
    public ResponseEntity<ResponseDetalleMovimientoCaja> detalleMovimientoCaja(
            @PathVariable long idMovimientoCaja,
            @PathVariable long idTurnoCaja) {

        ResponseDetalleMovimientoCaja response = detalleMovimientoCajaUseCase.DetalleMovimientoCaja(idMovimientoCaja, idTurnoCaja);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
