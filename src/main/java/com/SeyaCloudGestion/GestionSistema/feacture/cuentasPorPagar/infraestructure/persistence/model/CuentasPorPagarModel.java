package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CuentasPorPagarModel  extends AuditableModel implements Serializable {
    private long idCuentaPorPagar;
    private long idCompra;
    private double montoPendiente;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaVencimiento;
    //enum estado global
    private String estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}