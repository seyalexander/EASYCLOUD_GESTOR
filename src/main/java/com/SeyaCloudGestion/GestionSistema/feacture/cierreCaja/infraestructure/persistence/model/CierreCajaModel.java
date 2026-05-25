package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CierreCajaModel implements Serializable {
    private long idCierrecaja;
    private long idAperturaCaja;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCierre;
    private double montoSistema;
    private double montoReal;
    private double diferencia;

}