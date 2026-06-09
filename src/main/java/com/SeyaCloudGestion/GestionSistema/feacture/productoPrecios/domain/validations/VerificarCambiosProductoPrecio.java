package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestEditarAllProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.model.ProductoPrecioModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificarCambiosProductoPrecio implements GlobalVerficarCambios<ProductoPrecioModel, RequestEditarAllProductoPrecio> {
    @Override
    public boolean verificarCambios(ProductoPrecioModel modelBD, RequestEditarAllProductoPrecio request) {

        boolean cambioPrecio =
                Double.compare(modelBD.getPrecio(), request.getPrecio()) != 0;

        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        boolean cambioFechaInicio =
                (modelBD.getFechaInicio() == null && request.getFechaInicio() != null) ||
                        (modelBD.getFechaInicio() != null && request.getFechaInicio() == null) ||
                        (modelBD.getFechaInicio() != null && request.getFechaInicio() != null &&
                                !modelBD.getFechaInicio().toLocalDate()
                                        .equals(request.getFechaInicio().toLocalDate()));

        boolean cambioFechaFin =
                (modelBD.getFechaFin() == null && request.getFechaFin() != null) ||
                        (modelBD.getFechaFin() != null && request.getFechaFin() == null) ||
                        (modelBD.getFechaFin() != null && request.getFechaFin() != null &&
                                !modelBD.getFechaFin().toLocalDate()
                                        .equals(request.getFechaFin().toLocalDate()));
        return  cambioPrecio
                || cambioEstado
                || cambioFechaInicio
                || cambioFechaFin;
    }
}
