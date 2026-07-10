package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PagoProveedorModel implements Serializable {
    private long idPagoProveedor;
    private long idCuentaPorPagar;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaPago;;
    private double montoPagado;
    private long idTipoPago;
    //auditoria
    private long idUsuarioCreacion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCreacion;
}