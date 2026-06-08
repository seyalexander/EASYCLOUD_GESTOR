package com.SeyaCloudGestion.GestionSistema.feacture.marca.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request.RequestEditarAllMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.model.MarcaModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosMarca  implements GlobalVerficarCambios<MarcaModel, RequestEditarAllMarca> {
    @Override
    public boolean verificarCambios(MarcaModel modelBD, RequestEditarAllMarca request) {
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
