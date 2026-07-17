package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestDetallePorCodigoTipoMovimiento {
    @NotNull(message = "El código es obligatorio")
    private TipoMovimientoKardex codigo;
}
