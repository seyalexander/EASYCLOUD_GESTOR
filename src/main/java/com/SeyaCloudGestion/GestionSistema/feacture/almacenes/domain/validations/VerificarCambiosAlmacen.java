package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarAllAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenesModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosAlmacen implements GlobalVerficarCambios<AlmacenesModel, RequestEditarAllAlmacenes> {
    @Override
    public boolean verificarCambios(AlmacenesModel modelBD, RequestEditarAllAlmacenes request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioEstado;
    }
}
