package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestEditarAllCodigoBarra {
    @Min(value = 1, message = "El id del codigo debe ser mayor a 0")
    private long idCodigoBarra;

    @Min(value = 1, message = "El id del artículo debe ser mayor a 0")
    private long idArticulo;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 100, message = "El código no debe superar los 100 caracteres")
    private String codigo;

    @Min(value = 0, message = "El valor mínimo permitido para principal es 0")
    @Max(value = 1, message = "El valor máximo permitido para principal es 1")
    private int principal;
}
