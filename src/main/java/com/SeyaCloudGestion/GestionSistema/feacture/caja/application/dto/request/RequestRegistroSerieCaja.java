package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestRegistroSerieCaja {
    //@Min(value = 1, message = "El id de la caja debe ser mayor a 0")
    //private long idCaja;

    @Min(value = 1, message = "El id de la serie de documento debe ser mayor a 0")
    private long idSerieDocumento;
}