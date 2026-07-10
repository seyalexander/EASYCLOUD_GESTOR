package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.NumeroTelefonicoValidator.ValidarTelefono;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroProveedor {
    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 250, message = "La razón social no debe superar los 20 caracteres")
    private String razonSocial;

    @NotBlank(message = "El RUC es obligatorio")
    @Size(min = 11, max = 11, message = "El RUC debe tener 11 caracteres")
    private String ruc;

    @NotBlank(message = "El teléfono es obligatorio")
    @ValidarTelefono
    private String telefono;

    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150, message = "El email no debe superar los 150 caracteres")
    private String email;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 250, message = "La dirección no debe superar los 250 caracteres")
    private String direccion;

    @Min(value = 1, message = "El id de tipo documento debe ser mayor a 0")
    private long idTipoDocumento;

}