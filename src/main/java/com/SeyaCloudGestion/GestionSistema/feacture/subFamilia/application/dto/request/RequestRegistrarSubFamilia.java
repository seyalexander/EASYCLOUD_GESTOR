package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request;

import lombok.Data;

@Data
public class RequestRegistrarSubFamilia {
    private long idFamilia;
    private String subFamiliaDescripcion;
    private String imagenUrl;
}
