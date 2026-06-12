package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request.RequestEditarAllCodigoBarra;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.model.CodigoBarraModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosCodigoBarra implements GlobalVerficarCambios <CodigoBarraModel, RequestEditarAllCodigoBarra> {
    @Override
    public boolean verificarCambios(CodigoBarraModel modelBD, RequestEditarAllCodigoBarra request) {

        boolean cambioEstado =
                modelBD.getPrincipal() != request.getPrincipal();

        boolean cambioCodigo =
                !Objects.equals(
                modelBD.getCodigo(),
                request.getCodigo());

        return cambioEstado
                || cambioCodigo;
    }
}
