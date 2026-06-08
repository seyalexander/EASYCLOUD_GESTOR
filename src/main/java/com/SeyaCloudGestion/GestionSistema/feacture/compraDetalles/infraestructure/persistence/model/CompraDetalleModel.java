package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import lombok.Data;

@Data
public class CompraDetalleModel extends AuditableModel {
    private long idCompraDetalle;
    private long idCompra;
    private long idArticulo;
    private double cantidad;
    private double costoUnitario;
    private double total;
    private String fechaIngreso;
}
