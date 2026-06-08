package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.infraestructure.persistence.model.PagoClienteModel;
import lombok.Data;

@Data
public class NotificacionPagoClienteDTO extends PagoClienteModel {
    private String tipo;
    private String mensaje;
}
