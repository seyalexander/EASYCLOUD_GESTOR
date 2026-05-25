package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroCierreCaja {

    @Min(value = 1, message = "El id de apertura de caja debe ser mayor a 0")
    private long idAperturaCaja;

    @PositiveOrZero(message = "El monto del sistema no puede ser negativo")
    private double montoSistema;

    @PositiveOrZero(message = "El monto real no puede ser negativo")
    private double montoReal;

}