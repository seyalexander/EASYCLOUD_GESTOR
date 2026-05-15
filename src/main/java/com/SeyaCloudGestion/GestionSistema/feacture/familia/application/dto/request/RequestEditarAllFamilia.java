package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarAllFamilia {
    private long idFamilia;
    private String descripcion;
    private String imagenUrl;
    private int estado;
}
