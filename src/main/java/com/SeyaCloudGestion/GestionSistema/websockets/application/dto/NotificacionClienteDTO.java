package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.model.ClienteModel;
import lombok.Data;

@Data
public class NotificacionClienteDTO extends ClienteModel {
    private String tipo;
    private String mensaje;
}
