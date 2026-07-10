package com.SeyaCloudGestion.GestionSistema.feacture.empresa.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request.RequestEditarAllEmpresa;
import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.model.EmpresaModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosEmpresa implements GlobalVerficarCambios<EmpresaModel, RequestEditarAllEmpresa> {
    @Override
    public boolean verificarCambios(EmpresaModel modelBD, RequestEditarAllEmpresa request) {
        boolean cambioImagenUrl =
                !Objects.equals(modelBD.getImagenUrl(), request.getImagenUrl());

        boolean cambioRazonSocial =
                !Objects.equals(modelBD.getRazonSocial(), request.getRazonSocial());

        boolean cambioRuc =
                !Objects.equals(modelBD.getRuc(), request.getRuc());

        boolean cambioDireccion =
                !Objects.equals(modelBD.getDireccion(), request.getDireccion());

        boolean cambioTelefono =
                !Objects.equals(modelBD.getTelefono(), request.getTelefono());

        boolean cambioEmail =
                !Objects.equals(modelBD.getEmail(), request.getEmail());

        boolean cambioLogoUrl =
                !Objects.equals(modelBD.getLogoUrl(), request.getLogoUrl());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioImagenUrl
                || cambioRazonSocial
                || cambioRuc
                || cambioDireccion
                || cambioTelefono
                || cambioEmail
                || cambioLogoUrl
                || cambioEstado;
    }
}
