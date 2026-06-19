package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroKardex {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private TipoMovimientoKardex tipoMovimiento;

    @PositiveOrZero(message = "La cantidad de entrada no puede ser negativa")
    private double cantidadEntrada;
    @PositiveOrZero(message = "El costo de entrada no puede ser negativo")
    private double costoEntrada;

    @PositiveOrZero(message = "La cantidad de salida no puede ser negativa")
    private double cantidadSalida;
    @PositiveOrZero(message = "El costo de salida no puede ser negativo")
    private double costoSalida;

    //@PositiveOrZero(message = "El saldo de cantidad resultante no puede ser negativo")
    //private double saldoCantidad;
    //@PositiveOrZero(message = "El saldo del costo resultante no puede ser negativo")
    //private double saldoCosto;
}