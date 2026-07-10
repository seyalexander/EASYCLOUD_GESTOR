package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request.RequestEditarAllTipoPagos;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.model.TipoPagoModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosTipoPago  implements GlobalVerficarCambios<TipoPagoModel, RequestEditarAllTipoPagos> {
    @Override
    public boolean verificarCambios(TipoPagoModel modelBD, RequestEditarAllTipoPagos request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        boolean cambioImagen =
                !Objects.equals(
                        modelBD.getImagenUrl(),
                        request.getImagenUrl());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioImagen || cambioEstado;
    }
}
