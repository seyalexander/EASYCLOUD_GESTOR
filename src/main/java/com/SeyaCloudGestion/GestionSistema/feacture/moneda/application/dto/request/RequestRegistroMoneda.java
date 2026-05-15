package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request;

import lombok.Data;

@Data
public class RequestRegistroMoneda {
    private String descripcion;
    private String simbolo;
    private int esPrincipal;
}
