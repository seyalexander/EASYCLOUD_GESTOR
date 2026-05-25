package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AperturaCajaModel implements Serializable {

    private long idAperturacaja;
    private long idSucursal;
    private long idUsuario;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaApertura;
    private double montoInical;
    private Estado estado;

}