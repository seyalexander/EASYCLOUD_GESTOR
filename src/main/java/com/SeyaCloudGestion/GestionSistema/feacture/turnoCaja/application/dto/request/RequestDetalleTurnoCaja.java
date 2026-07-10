package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleTurnoCaja {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idCaja;

    //@NotNull(message = "El estado es obligatorio")
    //private EstadoCaja estado;

}
