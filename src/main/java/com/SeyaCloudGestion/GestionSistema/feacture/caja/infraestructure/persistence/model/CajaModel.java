package com.SeyaCloudGestion.GestionSistema.feacture.caja.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CajaModel implements Serializable {

    private long idCaja;
    private String descripcion;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private long idUsuarioCreacion;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaEdicion;
    private long idUsuarioEdicion;

}