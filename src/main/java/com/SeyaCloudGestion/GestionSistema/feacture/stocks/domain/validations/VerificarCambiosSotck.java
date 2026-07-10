package com.SeyaCloudGestion.GestionSistema.feacture.stocks.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request.RequestEditarAllSotck;
import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.model.SotckModel;
import org.springframework.stereotype.Component;

@Component
public class VerificarCambiosSotck implements GlobalVerficarCambios<SotckModel, RequestEditarAllSotck> {

    @Override
    public boolean verificarCambios(SotckModel modelBD, RequestEditarAllSotck request) {

        boolean cambioCantidad =
                modelBD.getStock() != request.getStock();

        return cambioCantidad ;
    }
}