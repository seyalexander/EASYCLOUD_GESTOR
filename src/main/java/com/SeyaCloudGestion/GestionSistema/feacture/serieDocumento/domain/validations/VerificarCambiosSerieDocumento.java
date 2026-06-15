package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request.RequestEditarAllSeries;
import com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.infraestructure.persistence.model.SerieDocumentoModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosSerieDocumento implements GlobalVerficarCambios<SerieDocumentoModel, RequestEditarAllSeries> {

    @Override
    public boolean verificarCambios(SerieDocumentoModel modelBD, RequestEditarAllSeries request) {

        boolean cambioSerie =
                !Objects.equals(
                        modelBD.getSerie(),
                        request.getSerie());

        boolean cambioEsElectronico =
                modelBD.getEsElectronico() != request.getEsElectronico();

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioSerie || cambioEsElectronico || cambioEstado;
    }
}