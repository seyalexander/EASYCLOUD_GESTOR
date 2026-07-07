package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model.Movimiento;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroMovimientoCaja {

    @Min(value = 1, message = "El id del turno caja de caja debe ser mayor a 0")
    private long idTurnoCaja;

    @Min(value = 1, message = "El id del turno caja de caja debe ser mayor a 0")
    private long idTipoPago;

    @NotNull(message = "El movimiento es obligatorio")
    private Movimiento movimiento;

    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 150, message = "El concepto no debe superar los 250 caracteres")
    private String concepto;

    @PositiveOrZero(message = "El monto no puede ser negativo")
    private double monto;

}