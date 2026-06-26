package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CuentasPorCobrarModel implements Serializable {
    private long idCuentaPorCobrar;
    private long idVenta;
    private double montoPendiente;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaVencimiento;
    private EstadoCuenta estado;
}
