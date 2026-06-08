package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.NumeroTelefonicoValidator.ValidarTelefono;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllCliente {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCliente;

    @Size(max = 150, message = "Los nombres no deben superar los 150 caracteres")
    private String nombres;

    @Size(max = 150, message = "Los apellidos no deben superar los 150 caracteres")
    private String apellidos;

    @Size(max = 200, message = "La razón social no debe superar los 200 caracteres")
    private String razonSocial;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El número de documento no debe superar los 20 caracteres")
    private String numeroDocumento;

    @Min(value = 1, message = "El id de tipo documento debe ser mayor a 0")
    private long idTipoDocumento;

    @Min(value = 1, message = "El id de tipo cliente debe ser mayor a 0")
    private long idTipoCliente;

    @NotBlank(message = "El teléfono es obligatorio")
    @ValidarTelefono
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 250, message = "El email no debe superar los 250 caracteres")
    private String email;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;
}
