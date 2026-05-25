package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class SerieDocumentoModel extends AuditableModel implements Serializable {
    private long idSerieDocumento;
    private long idTipoDocumento;
    private long idEmpresa;
    private String serie;
    private long correlativoActual;
    private int esElectronico;
    private int estado;
}