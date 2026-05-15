package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request;

import lombok.Data;

@Data
public class RequestRegistroUsuario {
    private String usuario;
    private String passowrd;
    private long idRol;
    private long idEmpleado;
}
