package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RequestRegistroProveedores {
    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 250, message = "La razón social no debe superar los 20 caracteres")
    private String razonSocial;

    @NotBlank(message = "El RUC es obligatorio")
    @Size(min = 11, max = 11, message = "El RUC debe tener 11 caracteres")
    private String ruc;

    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150, message = "El email no debe superar los 150 caracteres")
    private String email;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 250, message = "La dirección no debe superar los 250 caracteres")
    private String direccion;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}