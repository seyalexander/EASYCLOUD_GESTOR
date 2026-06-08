package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MovimientoCajaModel implements Serializable {
    private long idMovimientoCaja;
    private long idAperturaCaja;
    private Movimiento Movimiento;
    private String concepto;
    private double monto;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha;
}