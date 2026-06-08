package com.SeyaCloudGestion.GestionSistema.feacture.familia.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request.RequestEditarAllFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model.FamiliaModel;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class VerificarCambiosFamilia implements GlobalVerficarCambios<FamiliaModel, RequestEditarAllFamilia>
{
    @Override
    public boolean verificarCambios(FamiliaModel modelBD, RequestEditarAllFamilia request) {
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
