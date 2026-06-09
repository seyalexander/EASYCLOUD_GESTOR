package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ListaPreciosModel implements Serializable {

    private long idListaPrecios;
    private String descripcion;
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
