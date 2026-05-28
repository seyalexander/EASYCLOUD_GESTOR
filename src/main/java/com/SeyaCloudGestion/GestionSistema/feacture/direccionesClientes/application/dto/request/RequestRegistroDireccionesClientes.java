package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroDireccionesClientes {
    @Min(value = 1, message = "El id del cliente es obligatorio")
    private long idCliente;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no debe superar los 255 caracteres")
    private String direccion;

    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 100, message = "El departamento no debe superar los 100 caracteres")
    private String departamento;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 100, message = "La provincia no debe superar los 100 caracteres")
    private String provincia;

    @NotBlank(message = "El distrito es obligatorio")
    @Size(max = 100, message = "El distrito no debe superar los 100 caracteres")
    private String distrito;

    @NotBlank(message = "La referencia es obligatoria")
    @Size(max = 255, message = "La referencia no debe superar los 255 caracteres")
    private String referencia;

}