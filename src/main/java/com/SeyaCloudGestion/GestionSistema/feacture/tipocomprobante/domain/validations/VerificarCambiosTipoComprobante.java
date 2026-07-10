package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.validations;// Generado a partir de la arquitectura de subFamilia.
import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestEditarAllTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.model.TipoComprobanteModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosTipoComprobante implements GlobalVerficarCambios<TipoComprobanteModel, RequestEditarAllTipoComprobante> {

    @Override
    public boolean verificarCambios(TipoComprobanteModel modelBD, RequestEditarAllTipoComprobante request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        boolean cambioCodigo =
                !Objects.equals(
                        modelBD.getCodigoSunat(),
                        request.getCodigoSunat());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioCodigo || cambioEstado;
    }
}