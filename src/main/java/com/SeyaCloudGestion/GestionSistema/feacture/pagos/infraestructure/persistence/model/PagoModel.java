package com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class PagoModel extends AuditableModel implements Serializable {

    private long idPago;
    private long idVenta;
    private long idTipoPago;
    private long idSucursal;
    private double monto;
    private String referencia;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaPago;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
