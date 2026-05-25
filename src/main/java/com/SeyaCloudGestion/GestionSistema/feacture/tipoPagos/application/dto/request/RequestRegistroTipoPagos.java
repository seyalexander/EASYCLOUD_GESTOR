package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroTipoPagos {
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 250, message = "La descripción no debe superar los 250 caracteres")
    private String descripcion;

    @Size(max = 500, message = "La URL de la imagen no debe superar los 500 caracteres")
    private String imagenUrl;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;

}