package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroCodigoBarra {
    @Min(value = 1, message = "El id del artículo debe ser mayor a 0")
    private long idArticulo;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 250, message = "El código no debe superar los 250 caracteres")
    private String codigo;

    @Min(value = 0, message = "El valor mínimo permitido para principal es 0")
    @Max(value = 1, message = "El valor máximo permitido para principal es 1")
    private int principal;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
