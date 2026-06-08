package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request.RequestEditarAllSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.model.subFamiliaModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosSubFamilia implements GlobalVerficarCambios<subFamiliaModel, RequestEditarAllSubFamilia> {
    @Override
    public boolean verificarCambios(subFamiliaModel modelBD, RequestEditarAllSubFamilia request) {

        boolean cambioIdFamilia =
                modelBD.getIdFamilia() != request.getIdFamilia();

        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getSubFamiliaDescripcion(),
                        request.getSubFamiliaDescripcion());

        boolean cambioImagen =
                !Objects.equals(
                        modelBD.getImagenUrl(),
                        request.getImagenUrl());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioImagen || cambioIdFamilia || cambioEstado;

    }
}