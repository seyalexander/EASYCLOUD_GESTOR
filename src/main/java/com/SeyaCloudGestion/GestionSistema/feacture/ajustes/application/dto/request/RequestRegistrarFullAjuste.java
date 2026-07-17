package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.model.DetalleInventarioModel;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class RequestRegistrarFullAjuste {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idInventarioCabecera;
}
