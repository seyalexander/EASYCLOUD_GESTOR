package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestAceptrarTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseAceptarTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseRegistroTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.useCase.AceptarTransferenciaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.useCase.DetalleTransferenciaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.useCase.ListaTransferenciaUseCase;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.useCase.RegistroTransferenciaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transferencia")
public class TransferenciaController {

    @Autowired
    private ListaTransferenciaUseCase listaTransferenciaUseCase;

    @Autowired
    private RegistroTransferenciaUseCase registroTransferenciaUseCase;

    @Autowired
    private AceptarTransferenciaUseCase aceptarTransferenciaUseCase;

    @Autowired
    private DetalleTransferenciaUseCase detalleTransferenciaUseCase;

    @GetMapping
    @Operation(summary = "Listar transferencias", description = "Obtiene la lista de transferencias registradas")
    public ResponseEntity<ResponseListaTransferencia> listaTransferencia(
            @Validated @ModelAttribute RequestListaTransferencia request) {

        ResponseListaTransferencia response = listaTransferenciaUseCase.ListaTransferencia(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Registrar transferencia", description = "Registra una cabecera de transferencia junto con su detalle y descuenta stock del origen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transferencia registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseRegistroTransferencia> registroTransferencia(
            @Validated @RequestBody RequestRegistroTransferencia request) {

        ResponseRegistroTransferencia response = registroTransferenciaUseCase.RegistroTransferencia(request);

        if (response.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/aceptar")
    @Operation(summary = "Aceptar transferencia", description = "Acepta la transferencia ingresando la mercancía al almacén de destino y cambia el estado a FINALIZADO")
    public ResponseEntity<ResponseAceptarTransferencia> aceptarTransferencia(
            @Validated @RequestBody RequestAceptrarTransferencia request) {

        ResponseAceptarTransferencia response = aceptarTransferenciaUseCase.AceptarTransferencia(request);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{idTransferencia}")
    @Operation(summary = "Detalle transferencia", description = "Obtiene los datos de cabecera de una transferencia específica")
    public ResponseEntity<ResponseDetalleTransferencia> detalleTransferencia(
            @PathVariable long idTransferencia) {

        ResponseDetalleTransferencia response = detalleTransferenciaUseCase.DetalleTransferencia(idTransferencia);

        if (response.isExito()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}