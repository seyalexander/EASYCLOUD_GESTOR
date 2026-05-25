package com.SeyaCloudGestion.GestionSistema.feacture.usuarios.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.password.ValidPassword;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestRegistroUsuario {

    @NotBlank(message = "El nombre del usuario no puede estar vacio")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String usuario;

    @NotBlank(message = "La contraseña del usuario no puede estar vacia")
    @Size(max = 255, message = "La contraseña no debe superar los 255 caracteres")
    @ValidPassword
    private String passowrd;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idRol;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idEmpleado;
}
