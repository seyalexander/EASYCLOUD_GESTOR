package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.fechaPosterior.FechaFinPosterior;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@FechaFinPosterior(
        fechaInicio = "fechaInicio",
        fechaFin = "fechaFin",
        message = "La fecha fin debe ser posterior a la fecha inicio"
)
public class RequestListaKardex {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha fin es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaFin;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;
}
