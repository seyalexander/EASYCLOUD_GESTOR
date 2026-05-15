package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarAllTipoDocumento {
    private long idTipoDocumentos;
    private String descripcion;
    private int longitudMin;
    private int longitudMax;
    private int estado;
    private String codigoSunat;
    private int tipoCaracter;
}
