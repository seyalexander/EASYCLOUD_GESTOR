package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.validation.GlobalVerficarCambios;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request.RequestEditarAllProductoImpuesto;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.model.ProductoImpuestoModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VerificacionesProductoImpuesto implements GlobalVerficarCambios<ProductoImpuestoModel, RequestEditarAllProductoImpuesto> {
    @Override
    public  boolean verificarCambios(ProductoImpuestoModel modelBD, RequestEditarAllProductoImpuesto request) {
        boolean cambioDescripcion =
                !Objects.equals(
                        modelBD.getPorcentaje(),
                        request.getPorcentaje());
        boolean cambioEstado =
                modelBD.getEstado() != request.getEstado();

        return cambioDescripcion || cambioEstado;
    }
}
