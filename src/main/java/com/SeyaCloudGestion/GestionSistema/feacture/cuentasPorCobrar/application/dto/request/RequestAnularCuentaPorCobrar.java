package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestAnularCuentaPorCobrar {

    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idCuentaPorCobrar;

}