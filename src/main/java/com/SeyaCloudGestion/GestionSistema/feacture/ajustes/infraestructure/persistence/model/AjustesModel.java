package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AjustesModel  extends AuditableModel implements Serializable {
    private long idAjusteStock;
    private long idArticulo;
    private long idSucursal;
    private long idAlmacen;
    private double cantidad;
    private String motivo;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaAjuste;
    private long idUsuario;
}