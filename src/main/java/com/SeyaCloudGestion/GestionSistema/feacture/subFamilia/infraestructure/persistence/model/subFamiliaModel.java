package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class subFamiliaModel implements Serializable {

    private long idFamilia;
    private String familiaDescripcion;
    private long idSubFamilia;
    private String subFamiliaDescripcion;
    private String imagenUrl;
    private int estado;

    // Auditoría
    private String fechaCreacion;
    private String fechaEdicion;
    private String fechaAnulacion;
    private long idUsuarioCreacion;
    private long idUsuarioEdicion;
    private long idUsuarioAnulacion;
    private String usuarioCreacion;
    private String usuarioEdicion;
    private String usuarioAnulacion;
}
