package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestListaContactoCliente {
    @Schema(
            description = "Estado del contacto del cliente",
            example = "1",
            allowableValues = {"0: Inactivo", "1: Activo", "2: Todos"}
    )
    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 2, message = "El estado solo puede ser 0, 1 o 2")
    private int estado;

    @Min(value = 0, message = "El id del cliente no puede ser negativo")
    private long idCliente;
}
