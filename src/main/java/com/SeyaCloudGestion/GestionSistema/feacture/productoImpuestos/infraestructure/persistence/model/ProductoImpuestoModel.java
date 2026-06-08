package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class ProductoImpuestoModel extends AuditableModel  implements Serializable {
    private long idProductoImpuesto;
    private long idArticulo;
    private double porcentaje;
    private int estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
