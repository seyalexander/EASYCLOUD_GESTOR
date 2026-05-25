package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PagoProveedoresModel implements Serializable {
    private long idPagoProveedor;
    private long idCuentaPorPagar;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaPago;;
    private double montoPagado;
    //enum metodo pago global
    private String metodoPago;
}