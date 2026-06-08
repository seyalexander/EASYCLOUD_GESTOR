package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class DireccionesClientesModel extends AuditableModel implements Serializable {
    private long idDireccionCliente;
    private long idCliente;
    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;
    private String referencia;
    private int estado;
}