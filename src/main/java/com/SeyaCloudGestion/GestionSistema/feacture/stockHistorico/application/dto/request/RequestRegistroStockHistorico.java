package com.SeyaCloudGestion.GestionSistema.feacture.stockHistorico.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroStockHistorico {

    @Min(value = 1, message = "El id del artículo debe ser mayor a 0")
    private long idArticulo;

    @Min(value = 1, message = "El id del almacén debe ser mayor a 0")
    private long idAlmacen;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private double stock;

    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha;
}
