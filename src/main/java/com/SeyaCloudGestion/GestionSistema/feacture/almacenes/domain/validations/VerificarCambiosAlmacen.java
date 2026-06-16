package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarAllAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosAlmacen implements GlobalVerficarCambios<AlmacenModel, RequestEditarAllAlmacen> {
    @Override
    public boolean verificarCambios(AlmacenModel modelBD, RequestEditarAllAlmacen request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioEstado;
    }
}
