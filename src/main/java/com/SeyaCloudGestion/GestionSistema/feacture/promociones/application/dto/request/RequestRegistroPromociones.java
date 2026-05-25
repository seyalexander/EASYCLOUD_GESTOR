package com.SeyaCloudGestion.GestionSistema.feacture.promociones.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroPromociones {
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 250, message = "La descripción no debe superar los 250 caracteres")
    private String descripcion;

    @Min(value = 1, message = "El id de tipo promoción debe ser mayor a 0")
    private long idTipoPromocion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaFin;
}