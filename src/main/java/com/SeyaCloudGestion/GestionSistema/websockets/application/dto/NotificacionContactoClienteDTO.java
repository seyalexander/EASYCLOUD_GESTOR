package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.infraestructure.persistence.model.ContactoClienteModel;
import lombok.Data;

@Data
public class NotificacionContactoClienteDTO extends ContactoClienteModel {
    private String tipo;
    private String mensaje;
}
