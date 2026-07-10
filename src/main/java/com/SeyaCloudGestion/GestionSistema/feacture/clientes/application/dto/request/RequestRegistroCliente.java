package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.NumeroTelefonicoValidator.ValidarTelefono;
import com.SeyaCloudGestion.GestionSistema.common.anotations.letras.SoloLetras;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroCliente {

    @Size(max = 150, message = "Los nombres no deben superar los 150 caracteres")
    @SoloLetras
    private String nombres;

    @Size(max = 150, message = "Los apellidos no deben superar los 150 caracteres")
    @SoloLetras
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
}
