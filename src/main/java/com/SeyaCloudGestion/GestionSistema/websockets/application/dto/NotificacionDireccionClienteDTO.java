package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.model.DireccionesClientesModel;
import lombok.Data;

@Data
public class NotificacionDireccionClienteDTO extends DireccionesClientesModel {
    private String tipo;
    private String mensaje;
}
