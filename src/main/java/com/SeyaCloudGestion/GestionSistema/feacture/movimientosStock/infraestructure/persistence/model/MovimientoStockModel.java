package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class MovimientoStockModel extends AuditableModel implements Serializable {

    private long idMovimientoStock;
    private long idArticulo;
    private long idAlmacen;
    private long idTipoMovimiento;
    private double cantidad;
    private double costoUnitario;
    private String observacion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaMovimiento;
    private int estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
