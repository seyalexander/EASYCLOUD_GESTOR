package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductoPrecioModel extends AuditableModel {
    private long idProductoPrecio;
    private long idArticulo;
    private long idListaPrecio;
    private double precio;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int estado;
    private LocalDateTime fechaIngreso;
}
