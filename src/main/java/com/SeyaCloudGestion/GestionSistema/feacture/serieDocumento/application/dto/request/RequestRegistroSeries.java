package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroSeries {
    @Min(value = 1, message = "El id de tipo comprobante debe ser mayor a 0")
    private long idTipoComprobante;

    @NotBlank(message = "La serie es obligatoria")
    @Size(max = 10, message = "La serie no debe superar los 10 caracteres")
    private String serie;

    @Min(value = 0, message = "El valor mínimo permitido para electrónico es 0")
    @Max(value = 1, message = "El valor máximo permitido para electrónico es 1")
    private int esElectronico;
}
