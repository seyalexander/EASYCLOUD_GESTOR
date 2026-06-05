package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroAlmacenes {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 50, message = "La descripción no debe superar los 50 caracteres")
    private String descripcion;

    @Min(value = 1, message = "El id de sucursal debe ser mayor a 0")
    private long idSucursales;
}