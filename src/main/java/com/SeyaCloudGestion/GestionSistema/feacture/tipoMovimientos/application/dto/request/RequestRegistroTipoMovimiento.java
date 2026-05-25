package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroTipoMovimiento {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 250, message = "La descripción no debe superar los 250 caracteres")
    private String descripcion;

    @Min(value = 0, message = "El valor mínimo permitido para entrada es 0")
    @Max(value = 1, message = "El valor máximo permitido para entrada es 1")
    private int esEntrada;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
