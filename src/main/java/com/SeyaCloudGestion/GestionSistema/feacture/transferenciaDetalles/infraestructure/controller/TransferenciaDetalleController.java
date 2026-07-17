package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestListaDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response.ResponseListaTransferenciaDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.useCase.ListaDetalleTransferenciaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transferencia-detalle")
public class TransferenciaDetalleController {

    @Autowired
    private ListaDetalleTransferenciaUseCase listaDetalleTransferenciaUseCase;

    @GetMapping
    @Operation(summary = "Listar detalles de transferencia", description = "Obtiene los artículos pertenecientes al detalle de una transferencia específica")
    public ResponseEntity<ResponseListaTransferenciaDetalle> listaDetalleTransferencia(
            @Validated @ModelAttribute long idTransferencia) {

        ResponseListaTransferenciaDetalle response = listaDetalleTransferenciaUseCase.ListaDetalleTransferencia(idTransferencia);
        return ResponseEntity.ok(response);
    }
}