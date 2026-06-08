package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class NotaCreditoModel extends AuditableModel  implements Serializable {
    private long idNotaCredito;
    private long idVenta;
    private String motivo;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaEmision;
    private double total;
    private int estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
