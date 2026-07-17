package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.model.DetalleInventarioModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class RequestAjustarInventario {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idInventarioCabecera;
}
