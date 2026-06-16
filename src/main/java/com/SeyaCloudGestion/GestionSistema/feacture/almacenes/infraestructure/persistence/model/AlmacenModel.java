package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class AlmacenModel extends AuditableModel implements Serializable {
    private long idAlmacen;
    private String descripcion;
    private int estado;
    private long idSucursal;

}