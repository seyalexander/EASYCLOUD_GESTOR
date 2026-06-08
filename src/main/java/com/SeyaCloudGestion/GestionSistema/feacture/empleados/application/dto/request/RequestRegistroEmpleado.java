package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.NumeroTelefonicoValidator.ValidarTelefono;
import com.SeyaCloudGestion.GestionSistema.common.anotations.edadValidator.MayorDeEdad;
import com.SeyaCloudGestion.GestionSistema.common.anotations.noFechaFutura.NoFechaFutura;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroEmpleado {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no debe superar los 200 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 200, message = "El apellido no debe superar los 200 caracteres")
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @ValidarTelefono
    private String telefono;

    @Size(max = 500, message = "La URL de la imagen no debe superar los 500 caracteres")
    private String imagenUrl;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoDocumento;

    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 20, message = "El documento no debe superar los 20 caracteres")
    private String documento;

    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Formato inválido yyyy-MM-dd"
    )
    @MayorDeEdad
    private String fechaNacimiento;

    @NotBlank(message = "La fecha de ingreso es obligatoria")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Formato inválido yyyy-MM-dd"
    )
    @NoFechaFutura
    private String fechaIngreso;
}
