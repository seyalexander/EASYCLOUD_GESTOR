package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.login.infraestructura.persistence.Model.UsuarioModel;
import lombok.Data;

@Data
public class NotificacionLoginUsuarioDTO extends UsuarioModel {
    private String tipo;
    private String mensaje;
}
