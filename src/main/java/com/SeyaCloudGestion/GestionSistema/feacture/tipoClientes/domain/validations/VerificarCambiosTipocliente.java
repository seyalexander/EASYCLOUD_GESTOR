package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.model.TipoClientesModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosTipocliente implements GlobalVerficarCambios<TipoClientesModel, RequestEditarAllTipoClientes> {
    @Override
    public boolean verificarCambios(TipoClientesModel modelBD, RequestEditarAllTipoClientes request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioEstado;
    }
}
