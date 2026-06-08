package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RequestRegistroPagoProveedores {
    @Min(value = 1, message = "El id de cuenta por pagar debe ser mayor a 0")
    private long idCuentaPorPagar;

    @NotNull(message = "La fecha de pago es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaPago;

    @PositiveOrZero(message = "El monto pagado no puede ser negativo")
    private double montoPagado;

    @NotBlank(message = "El método de pago es obligatorio")
    @Size(max = 50, message = "El método de pago no debe superar los 50 caracteres")
    private String metodoPago;
}