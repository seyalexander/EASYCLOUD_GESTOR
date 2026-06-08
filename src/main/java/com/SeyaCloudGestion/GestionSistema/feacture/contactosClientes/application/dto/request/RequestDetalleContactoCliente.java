package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestDetalleContactoCliente {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idContactoCliente;
}
