package com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.useCase.ListaPagoUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.useCase.RegistroPagoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pagos")
@CrossOrigin(origins = "http://localhost:4200")
public class PagoController {

    @Autowired
    private ListaPagoUseCase listaPagoUseCase;

    @Autowired
    private RegistroPagoUseCase registroPagoUseCase;
    /*
    @GetMapping
    @Operation(summary = "Listar pagos de una venta", description = "Obtiene la lista de pagos asociados a una venta específica filtrada por Tenant (Empresa y Sucursal)")
    public ResponseEntity<ResponseListaPago> listaPago(@Validated @ModelAttribute RequestListaPago request) {

        ResponseListaPago response = listaPagoUseCase.ListaPago(request);

        return ResponseEntity.ok(response);
    }
     */

    @PostMapping
    @Operation(summary = "Registrar un pago", description = "Permite registrar un nuevo flujo de pago para una transacción de venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroPago> registroPago(
            @Validated @RequestBody RequestRegistroPago request) {

        ResponseRegistroPago response = registroPagoUseCase.registrarPago(request);

        if (response.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}