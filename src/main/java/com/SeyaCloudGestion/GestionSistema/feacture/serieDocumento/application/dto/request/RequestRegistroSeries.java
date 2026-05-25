package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroSeries {
    @Min(value = 1, message = "El id de tipo documento debe ser mayor a 0")
    private long idTipoDocumento;

    @Min(value = 1, message = "El id de empresa debe ser mayor a 0")
    private long idEmpresa;

    @NotBlank(message = "La serie es obligatoria")
    @Size(max = 250, message = "La serie no debe superar los 250 caracteres")
    private String serie;

    @PositiveOrZero(message = "El correlativo actual no puede ser negativo")
    private long correlativoActual;

    @Min(value = 0, message = "El valor mínimo permitido para electrónico es 0")
    @Max(value = 1, message = "El valor máximo permitido para electrónico es 1")
    private int esElectronico;
}
