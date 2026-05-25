package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;

public class ProductoPrecioModel extends AuditableModel {
    private long idProductoPrecio;
    private long idArticulo;
    private long idListaPrecio;
    private double precio;
    private String fechaInicio;
    private String fechaFin;
    private int estado;
    private String fechaIngreso;
}
