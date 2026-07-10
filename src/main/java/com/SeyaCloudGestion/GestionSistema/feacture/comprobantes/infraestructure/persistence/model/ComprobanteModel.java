package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class ComprobanteModel extends AuditableModel implements Serializable {
    private long idComprobante;
    private long idVenta;
    private long idTipoDocumento;
    private long idSerieDocumento;
    private String numero;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaEmision;
    private String urlXml;
    private String urlPdf;
    private EstadoComprobante estado;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
