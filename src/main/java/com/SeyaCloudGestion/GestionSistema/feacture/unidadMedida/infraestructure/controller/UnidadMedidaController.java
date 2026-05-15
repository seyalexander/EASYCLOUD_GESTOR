package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request.RequestListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseListaUnidadMedida;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.useCase.ListaUnidadMedidaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/unidadMedida")
public class UnidadMedidaController {

    @Autowired
    private ListaUnidadMedidaUseCase listaUnidadMedidaUseCase;

    @GetMapping
    @Operation(summary = "Listar unidad de medida by estado", description = "Obtiene la lista de unidad de medida según su estado")
    public ResponseEntity<ResponseListaUnidadMedida> listaUnidadMedida(@Validated @ModelAttribute RequestListaUnidadMedida request) {
        ResponseListaUnidadMedida response = listaUnidadMedidaUseCase.listaUnidadMedida(request);
        return ResponseEntity.ok(response);
    }
}
