package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestEditarAllTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.model.TipoMovimientoModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosTipoMovimiento  implements GlobalVerficarCambios<TipoMovimientoModel, RequestEditarAllTipoMovimiento> {
    @Override
    public boolean verificarCambios(TipoMovimientoModel modelBD, RequestEditarAllTipoMovimiento request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioEstado;
    }
}
