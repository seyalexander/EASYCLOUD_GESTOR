package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class SucursalesModel extends AuditableModel implements Serializable {
    private long idSucursales;
    private String descripcion;
    private int estado;
}
