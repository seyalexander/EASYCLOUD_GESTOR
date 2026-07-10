package com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class VentaModel  extends AuditableModel implements Serializable {

    private long idVenta;
    private long idCliente;
    private long idUsuario;
    private long idSucursal;
    private long idTurnoCaja;
    private CondicionPago condicionPago;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaVenta;
    private double subTotal;
    private double impuesto;
    private double total;
    private int estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}