package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.NumeroTelefonicoValidator.ValidarTelefono;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllEmpresa {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idEmpresa;

    @Size(max = 500, message = "La URL de la imagen es demasiado larga")
    private String imagenUrl;

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 255, message = "La razón social no debe superar los 255 caracteres")
    private String razonSocial;

    @NotBlank(message = "El RUC es obligatorio")
    @Pattern(regexp = "^[0-9]{11}$", message = "El RUC debe contener exactamente 11 dígitos")
    private String ruc;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no debe superar los 255 caracteres")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @ValidarTelefono
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 255, message = "El email no debe superar los 255 caracteres")
    private String email;

    @Size(max = 500, message = "La URL del logo es demasiado larga")
    private String logoUrl;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;
}
