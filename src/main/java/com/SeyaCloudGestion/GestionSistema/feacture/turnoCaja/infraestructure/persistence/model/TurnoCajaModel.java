package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TurnoCajaModel implements Serializable {
    private long idTurnoCaja;
    private long idUsuario;
    private long idCaja;
    private long idSucursal;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaApertura;
    private double montoInicial;
    private EstadoCaja estado;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCierre;
    private double montoSistema;
    private double montoReal;
    private double diferencia;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCreacion;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaEdicion;

    private long idUsuarioCreacion;
    private long idUsuarioEdicion;
    private String usuarioCreacion;
    private String usuarioEdicion;
}