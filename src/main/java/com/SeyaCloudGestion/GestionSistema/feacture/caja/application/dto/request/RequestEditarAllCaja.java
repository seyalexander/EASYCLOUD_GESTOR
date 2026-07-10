package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestEditarAllCaja {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCaja;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no debe superar los 100 caracteres")
    private String descripcion;
}