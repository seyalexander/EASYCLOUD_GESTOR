package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request.RequestEditarAllSucursales;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.model.SucursalesModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosSucursal implements GlobalVerficarCambios<SucursalesModel, RequestEditarAllSucursales> {
    @Override
    public boolean verificarCambios(SucursalesModel modelBD, RequestEditarAllSucursales request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioEstado;
    }
}
