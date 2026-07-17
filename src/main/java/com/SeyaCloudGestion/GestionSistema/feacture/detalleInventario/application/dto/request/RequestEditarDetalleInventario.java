package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class RequestEditarDetalleInventario {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @NotNull(message = "El stock físico es obligatorio")
    @PositiveOrZero(message = "El stock físico debe ser mayor o igual a 0")
    private double stockFisico;
}
