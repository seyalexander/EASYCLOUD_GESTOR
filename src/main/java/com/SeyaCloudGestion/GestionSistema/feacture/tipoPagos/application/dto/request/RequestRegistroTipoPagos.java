package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroTipoPagos {
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no debe superar los 100 caracteres")
    private String descripcion;

    @Size(max = 500, message = "La URL de la imagen no debe superar los 500 caracteres")
    private String imagenUrl;

}