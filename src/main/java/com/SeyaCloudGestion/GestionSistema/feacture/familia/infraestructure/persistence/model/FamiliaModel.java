package com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({ "idFamilia", "descripcion", "imagenUrl", "estado" })
public class FamiliaModel implements Serializable {
    private long idFamilia;
    private String descripcion;
    private String imagenUrl;
    private int estado;

    // Auditoría
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCreacion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaEdicion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaAnulacion;
    private long idUsuarioCreacion;
    private long idUsuarioEdicion;
    private long idUsuarioAnulacion;
    private String usuarioCreacion;
    private String usuarioEdicion;
    private String usuarioAnulacion;
}
