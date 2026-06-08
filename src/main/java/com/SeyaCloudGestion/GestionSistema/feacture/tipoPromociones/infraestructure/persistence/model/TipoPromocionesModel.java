package com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TipoPromocionesModel extends AuditableModel implements Serializable {
    private long idTipoPromocion;
    private String descripcion;
    private int estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}