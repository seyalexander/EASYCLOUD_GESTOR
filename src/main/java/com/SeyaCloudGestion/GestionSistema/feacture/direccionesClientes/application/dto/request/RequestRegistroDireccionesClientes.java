package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroDireccionesClientes {
    @Min(value = 1, message = "El id de cliente debe ser mayor a 0")
    private long idCliente;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 250, message = "La dirección no debe superar los 250 caracteres")
    private String direccion;

    @Size(max = 250, message = "El departamento no debe superar los 250 caracteres")
    private String departamento;

    @Size(max = 250, message = "La provincia no debe superar los 250 caracteres")
    private String provincia;

    @Size(max = 250, message = "El distrito no debe superar los 250 caracteres")
    private String distrito;

    @Size(max = 250, message = "La referencia no debe superar los 250 caracteres")
    private String referencia;
}