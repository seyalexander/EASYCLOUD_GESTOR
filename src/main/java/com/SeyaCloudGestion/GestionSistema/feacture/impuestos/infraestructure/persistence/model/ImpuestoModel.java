package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImpuestoModel implements Serializable {
    private long idImpuesto;
    private String descripcion;
    private double porcentaje;
    private int esPrincipal;
    private int estado;
}
