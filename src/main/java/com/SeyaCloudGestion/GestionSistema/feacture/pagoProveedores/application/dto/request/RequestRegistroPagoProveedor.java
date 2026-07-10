package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroPagoProveedor {
    @Min(value = 1, message = "El id de cuenta por pagar debe ser mayor a 0")
    private long idCuentaPorPagar;

    @PositiveOrZero(message = "El monto pagado no puede ser negativo")
    private double montoPagado;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoPago;

    @Min(value = 1, message = "El id de la caja debe ser mayor a 0")
    private long idCaja;
}