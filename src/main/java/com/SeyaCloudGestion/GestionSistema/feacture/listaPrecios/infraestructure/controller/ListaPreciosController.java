package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestListarListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseListaListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase.ListaListaPreciosUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/listaPrecios")
public class ListaPreciosController {

    @Autowired
    private ListaListaPreciosUseCase listaPreciosUseCase;

    @GetMapping
    @Operation(summary = "Listar lista precios by estado", description = "Obtiene la lista de lista de precios según su estado")
    public ResponseEntity<ResponseListaListaPrecios> listaListaPrecios(@Validated @ModelAttribute RequestListarListaPrecios request) {
        ResponseListaListaPrecios response = listaPreciosUseCase.listaListaPrecios(request);
        return ResponseEntity.ok(response);
    }
}
