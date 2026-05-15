package com.SeyaCloudGestion.GestionSistema.feacture.rol.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarAllRol {
    private long idRol;
    private String descripcion;
    private int estado;
}
