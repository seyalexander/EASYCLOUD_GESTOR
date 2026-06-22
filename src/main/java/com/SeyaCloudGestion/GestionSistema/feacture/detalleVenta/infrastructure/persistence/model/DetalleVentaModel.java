package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.infrastructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetalleVentaModel extends AuditableModel {

    private long idVentaDetalle;
    private long idVenta;
    private long idArticulo;
    private long idAlmacen;

    private double cantidad;
    private double precioUnitario;
    private double descuento;
    private double total;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;

}
