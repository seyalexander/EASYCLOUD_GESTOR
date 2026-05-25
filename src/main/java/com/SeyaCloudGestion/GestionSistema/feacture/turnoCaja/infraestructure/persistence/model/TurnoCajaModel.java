package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TurnoCajaModel implements Serializable {
    private long idTurnoCaja;
    private long idUsuario;
    private long idSucursal;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaApertura;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCierre;
    private double montoInicial;
    private double montoFinal;
    private String estado;
}