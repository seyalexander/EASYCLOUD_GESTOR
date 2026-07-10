package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DevolucionModel extends AuditableModel {
    private long idDevolucion;
    private long idDetalleVenta;
    private long idArticulo;
    private double cantidad;
    private String motivo;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaDevolucion;
    private long idUsuario;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
