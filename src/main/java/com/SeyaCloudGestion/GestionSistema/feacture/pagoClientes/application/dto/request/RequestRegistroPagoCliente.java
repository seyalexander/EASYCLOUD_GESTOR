package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class RequestRegistroPagoCliente {
    @Min(value = 1, message = "El id de cuenta por cobrar debe ser mayor a 0")
    private long idCuentaPorCobrar;

    @NotEmpty(message = "Debe registrar al menos un método de pago")
    private List<RequestRegistroDetallePagoCliente> pagos;
}
