package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestRegistroDetallePagoCliente {
    @Min(value = 1, message = "El id del tipo pago debe ser mayor a 0")
    private long idTipoPago;

    @Positive(message = "El monto pagado debe ser mayor a 0")
    private double montoPagado;
}