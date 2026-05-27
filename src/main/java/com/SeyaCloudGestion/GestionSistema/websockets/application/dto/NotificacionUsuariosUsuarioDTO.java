package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import lombok.Data;

@Data
public class NotificacionUsuariosUsuarioDTO extends UsuarioModel {
    private String tipo;
    private String mensaje;
}
