package com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class VentaModel  extends AuditableModel implements Serializable {
    private long idVenta;
    private long idCliente;
    private long idUsuario;
    private long idSucursal;
    private long idTurnoCaja;
    private String fechaVenta;
    private double subTotal;
    private double impuesto;
    private double total;
    private int estado;
    private String fechaIngreso;
}