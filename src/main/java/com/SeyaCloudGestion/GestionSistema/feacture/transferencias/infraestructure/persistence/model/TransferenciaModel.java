package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model;

import com.SeyaCloudGestion.GestionSistema.common.model.AuditableModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TransferenciaModel extends AuditableModel implements Serializable {
    private long idTransferencia;
    private long idAlmacenOrigen;
    private long idAlmacenDestino;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha;
    private EstadoTransferencia estado;
}
