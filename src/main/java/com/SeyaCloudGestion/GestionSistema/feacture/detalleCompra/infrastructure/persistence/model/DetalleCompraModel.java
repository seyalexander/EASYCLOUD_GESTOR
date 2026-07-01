package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.infrastructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetalleCompraModel extends AuditableModel {

    private long idCompraDetalle;
    private long idCompra;
    private long idArticulo;

    private double cantidad;
    private double costoUnitario;
    private double total;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;

}
