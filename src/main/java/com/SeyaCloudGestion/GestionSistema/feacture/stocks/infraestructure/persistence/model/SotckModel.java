package com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class SotckModel extends AuditableModel implements Serializable {
    private long idStock;
    private double stock;
    private long idProducto;
    private String descripcionProducto;
    private long idAlmacen;
    private String descripcionAlmacen;
    private long idSucursal;
    private String descripcionSucursal;
}