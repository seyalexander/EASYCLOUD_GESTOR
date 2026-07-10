package com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestEditarAllArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.model.ArticulosModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificacionesArticulo implements GlobalVerficarCambios<ArticulosModel, RequestEditarAllArticulo> {
    @Override
    public boolean verificarCambios(ArticulosModel modelBD, RequestEditarAllArticulo request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getDescripcion(),
                        request.getDescripcion());
        boolean cambioPrecio =
                modelBD.getPrecioVenta() != request.getPrecioVenta();

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioPrecio || cambioEstado;
    }
}
