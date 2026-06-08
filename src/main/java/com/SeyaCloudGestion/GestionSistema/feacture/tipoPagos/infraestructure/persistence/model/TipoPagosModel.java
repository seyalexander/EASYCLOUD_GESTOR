package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TipoPagosModel extends AuditableModel implements Serializable  {
    private long idTipoPago;
    private String descripcion;
    private String imagenUrl;
    private int estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}