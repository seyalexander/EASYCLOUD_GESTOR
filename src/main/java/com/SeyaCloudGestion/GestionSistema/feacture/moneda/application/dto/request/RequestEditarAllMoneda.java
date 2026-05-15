package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarAllMoneda {
    private long idMoneda;
    private String descripcion;
    private String simbolo;
    private int esPrincipal;
    private int estado;
}
