package com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PromocionesModel  implements Serializable {
    private long idPromocion;
    private String descripcion;
    private long idTipoPromocion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaInicio;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaFin;
    private int estado;
}