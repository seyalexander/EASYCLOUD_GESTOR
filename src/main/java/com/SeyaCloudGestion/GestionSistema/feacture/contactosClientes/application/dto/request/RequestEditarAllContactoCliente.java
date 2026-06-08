package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllContactoCliente {
    @Min(value = 1, message = "El id del contacto del cliente debe ser mayor a 0")
    private long idContactoCliente;

    @Min(value = 1, message = "El id del cliente debe ser mayor a 0")
    private long idCliente;

    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Size(max = 150, message = "El nombre del contacto no debe superar los 150 caracteres")
    private String nombreContacto;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no debe superar los 20 caracteres")
    private String telefono;

    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150, message = "El email no debe superar los 150 caracteres")
    private String email;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;
}
