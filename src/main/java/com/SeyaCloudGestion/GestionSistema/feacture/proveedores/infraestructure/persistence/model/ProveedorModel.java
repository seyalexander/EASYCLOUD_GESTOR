package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ProveedorModel extends AuditableModel implements Serializable {
    private long idProveedor;
    private String razonSocial;
    private String ruc;
    private long idTipoDocumento;
    private String telefono;
    private String email;
    private String direccion;
    private int estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}