package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class VentaResumenDiarioModel implements Serializable {
    private long idVentaresumendiario;
    private long idSucursal;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha;
    private double montoInical;
    private double totalImpuestos;
    private double totalNeto;
}