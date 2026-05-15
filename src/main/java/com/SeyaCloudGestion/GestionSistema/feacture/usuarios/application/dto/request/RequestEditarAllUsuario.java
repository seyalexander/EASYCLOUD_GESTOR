package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request;

import lombok.Data;

@Data
public class RequestEditarAllUsuario {
    private long idUsuario;
    private String usuario;
    private String passowrd;
    private int estado;
    private long idRol;
    private long idEmpleado;
}
