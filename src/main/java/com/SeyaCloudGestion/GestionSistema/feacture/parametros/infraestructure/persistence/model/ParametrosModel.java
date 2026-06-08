package com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParametrosModel implements Serializable {
    private long idParametroSistema;
    private String clave;
    private String valor;
    private String descripcion;
    private int estado;
}