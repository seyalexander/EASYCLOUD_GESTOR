package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SerieCajaModel implements Serializable {

    private long idCajaSerie;
    private long idCaja;
    private long idSerieDocumento;
    private String serie;
    private String tipoComprobante;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private long idUsuarioCreacion;
    private String usuarioCreacion;

}
