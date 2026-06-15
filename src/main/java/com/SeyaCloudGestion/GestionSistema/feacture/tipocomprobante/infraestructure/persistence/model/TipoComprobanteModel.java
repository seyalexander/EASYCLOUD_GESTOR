package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.model;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonPropertyOrder({ "idTipoComprobante", "descripcion", "codigoSunat", "estado" })
public class TipoComprobanteModel extends AuditableModel implements Serializable {

    private long idTipoComprobante;
    private String descripcion;
    private String codigoSunat;
    private int estado;

}
