package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model.EstadoTransferencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DetalleTransferenciaModel implements Serializable {
    private long idTransferenciaDetalle;
    private long idTransferencia;
    private long idArticulo;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private double cantidad;
    private double costoUnitario;
}
