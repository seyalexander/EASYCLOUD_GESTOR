package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroContactoCliente {
    @Min(value = 1, message = "El id de cliente debe ser mayor a 0")
    private long idCliente;

    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Size(max = 250, message = "El nombre del contacto no debe superar los 250 caracteres")
    private String nombreContacto;

    @Size(max = 20, message = "El teléfono no debe superar los 20 caracteres")
    private String telefono;

    @Email(message = "El email no tiene un formato válido")
    @Size(max = 250, message = "El email no debe superar los 250 caracteres")
    private String email;
}
