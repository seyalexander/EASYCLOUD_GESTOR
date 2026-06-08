package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AlmacenesModel extends AuditableModel implements Serializable {
    private long idAlmacenes;
    private String descripcion;
    private int estado;
    private long idSucursales;

}