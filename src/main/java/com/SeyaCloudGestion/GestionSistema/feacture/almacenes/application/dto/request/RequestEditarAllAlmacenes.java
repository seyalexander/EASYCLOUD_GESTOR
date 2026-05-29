package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllAlmacenes {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacenes;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 50, message = "La descripción no debe superar los 50 caracteres")
    private String descripcion;

    @Min(value = 1, message = "El id de la sucursal es obligatorio")
    private long idSucursales;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;

}