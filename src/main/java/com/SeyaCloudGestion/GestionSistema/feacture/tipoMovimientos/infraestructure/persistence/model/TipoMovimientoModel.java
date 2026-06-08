package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class TipoMovimientoModel extends AuditableModel implements Serializable {
    private long idTipoMovimiento;
    private String descripcion;
    private int esEntrada;
    private int estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
