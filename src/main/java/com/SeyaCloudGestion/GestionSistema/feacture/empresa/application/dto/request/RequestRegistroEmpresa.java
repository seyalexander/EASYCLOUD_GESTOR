package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request;

import lombok.Data;

@Data
public class RequestRegistroEmpresa {
    private String imagenUrl;
    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private String logoUrl;
}
