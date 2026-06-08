package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroPago {
    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idVenta;

    @Min(value = 1, message = "El id de tipo pago debe ser mayor a 0")
    private long idTipoPago;

    @PositiveOrZero(message = "El monto no puede ser negativo")
    private double monto;

    @Size(max = 250, message = "La referencia no debe superar los 250 caracteres")
    private String referencia;

    @NotNull(message = "La fecha de pago es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaPago;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
