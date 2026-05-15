package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarAllEmpresa {
    private long idEmpresa;
    private String imagenUrl;
    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private String logoUrl;
    private int estado;
}
