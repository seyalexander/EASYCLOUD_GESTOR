package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TipoClientesModel implements Serializable {
    private long idTipoCliente;
    private String descripcion;
    private int estado;
}