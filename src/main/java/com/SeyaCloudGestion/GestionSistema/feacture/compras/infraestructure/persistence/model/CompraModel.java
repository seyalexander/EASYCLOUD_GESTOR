package com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompraModel implements Serializable {
    private long idCompra;
    private long idProveedor;
    private long idSucursal;
    private String fechaCompra;
    private double subTotal;
    private double impuesto;
    private double total;
    private int estado;
    private String fechaIngreso;
}
