package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PagoClienteModel {
    private long idPagoCliente;
    private long idCuentaPorCobrar;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaPago;
    private double montoPagado;
    private String metodoPago;
}
