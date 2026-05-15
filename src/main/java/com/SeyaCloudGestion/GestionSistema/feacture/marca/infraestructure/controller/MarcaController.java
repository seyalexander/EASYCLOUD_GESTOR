package com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.controller;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseListaMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.useCase.ListaMarcaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/marca")
public class MarcaController {

    @Autowired
    private ListaMarcaUseCase listaMarcaUseCase;

    @GetMapping
    @Operation(summary = "Listar marca by estado", description = "Obtiene la lista de marcas según su estado")
    public ResponseEntity<ResponseListaMarca> listaMarcas(@Validated @ModelAttribute RequestListaMarca request) {
        ResponseListaMarca response = listaMarcaUseCase.listaMarcas(request);
        return ResponseEntity.ok(response);
    }
}
