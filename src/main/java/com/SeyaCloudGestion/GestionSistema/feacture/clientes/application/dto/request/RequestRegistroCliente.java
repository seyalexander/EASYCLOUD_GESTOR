package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroCliente {
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 250, message = "Los nombres no deben superar los 250 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 250, message = "Los apellidos no deben superar los 250 caracteres")
    private String apellidos;

    @Size(max = 250, message = "La razón social no debe superar los 250 caracteres")
    private String razonSocial;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El número de documento no debe superar los 20 caracteres")
    private String numeroDocumento;

    @Min(value = 1, message = "El id de tipo documento debe ser mayor a 0")
    private long idTipoDocumento;

    @Min(value = 1, message = "El id de tipo cliente debe ser mayor a 0")
    private long idTipoCliente;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no debe superar los 20 caracteres")
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 250, message = "El email no debe superar los 250 caracteres")
    private String email;
}
