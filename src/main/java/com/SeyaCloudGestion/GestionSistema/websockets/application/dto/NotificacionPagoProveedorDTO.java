package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model.PagoProveedorModel;
import lombok.Data;

@Data
public class NotificacionPagoProveedorDTO extends PagoProveedorModel {
    private String tipo;
    private String mensaje;
}
