package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.usuarios.infraestructure.persistence.model.UsuariosModel;
import lombok.Data;

@Data
public class NotificacionUsuarioDTO extends UsuariosModel {
    private String tipo;
    private String mensaje;
}
