package com.SeyaCloudGestion.GestionSistema.feacture.login.infraestructura.persistence.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {
    private int idUsuario;
    private String nombres;
    private String apellidos;
}
