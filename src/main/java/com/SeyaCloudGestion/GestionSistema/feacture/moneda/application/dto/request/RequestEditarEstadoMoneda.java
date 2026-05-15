package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarEstadoMoneda {
    private long idMoneda;
    private int estado;
}
