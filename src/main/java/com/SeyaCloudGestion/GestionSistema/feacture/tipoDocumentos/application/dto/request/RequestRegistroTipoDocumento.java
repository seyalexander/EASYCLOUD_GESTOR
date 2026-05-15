package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request;

import lombok.Data;

@Data
public class RequestRegistroTipoDocumento {
    private String descripcion;
    private int longitudMin;
    private int longitudMax;
    private String codigoSunat;
    private int tipoCaracter;
}
