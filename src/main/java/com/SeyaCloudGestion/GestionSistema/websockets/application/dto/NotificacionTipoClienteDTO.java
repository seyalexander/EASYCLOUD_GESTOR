package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.model.TipoClientesModel;
import lombok.Data;

@Data
public class NotificacionTipoClienteDTO extends TipoClientesModel {
    private String tipo;
    private String mensaje;
}
