package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarAllSubFamilia {
    private long idFamilia;
    private long idSubFamilia;
    private String subFamiliaDescripcion;
    private String imagenUrl;
    private int estado;
    private long idUsuarioEdicion;
}
