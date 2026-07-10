package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;

public class ValidacionRequest_RegistrarProductoImpuesto {


    public static void validar(ResponseDetalleArticulo detalleArt) {

        if (!detalleArt.isExito() || detalleArt.getArticulos() == null) {
            throw new ResourceNotFoundException("El articulo no existe.");
        }
    }
}
