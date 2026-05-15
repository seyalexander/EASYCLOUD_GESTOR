package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UnidadMedidaModel implements Serializable {

    private long idUnidadMedida;
    private String descripcion;
    private String  siglas;
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
