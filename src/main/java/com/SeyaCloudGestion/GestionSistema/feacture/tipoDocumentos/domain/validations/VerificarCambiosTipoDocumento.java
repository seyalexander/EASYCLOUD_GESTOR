package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestEditarAllTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoDocumentoModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosTipoDocumento implements GlobalVerficarCambios<TipoDocumentoModel, RequestEditarAllTipoDocumento> {
    @Override
    public boolean verificarCambios(TipoDocumentoModel modelBD, RequestEditarAllTipoDocumento request) {

        boolean cambioDescripcion =
                !Objects.equals(modelBD.getDescripcion(), request.getDescripcion());

        boolean cambioLongitudMin =
                modelBD.getLongitudMin() != request.getLongitudMin();

        boolean cambioLongitudMax =
                modelBD.getLongitudMax() != request.getLongitudMax();

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        boolean cambioCodigoSunat =
                !Objects.equals(modelBD.getCodigoSunat(), request.getCodigoSunat());

        boolean cambioTipoCaracter =
                !Objects.equals(modelBD.getTipoCaracter(), request.getTipoCaracter());

        return cambioDescripcion
                || cambioLongitudMin
                || cambioLongitudMax
                || cambioEstado
                || cambioCodigoSunat
                || cambioTipoCaracter;
    }
}
