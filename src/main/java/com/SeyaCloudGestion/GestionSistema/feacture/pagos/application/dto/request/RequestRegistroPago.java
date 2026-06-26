package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;


@Data
public class RequestRegistroPago  {

    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idVenta;

    @Min(value = 1, message = "El id de la caja debe ser mayor a 0")
    private long idCaja;

    @NotEmpty(message = "Debe registrar al menos un método de pago")
    private List<RequestRegistroDetallePago> pagos;
}
