package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseDetalleListaPrecios;

public class ValidacionRequest_RegistrarProductoPrecio {

    public static void validarRegistroArticulo(
            ResponseDetalleArticulo detalleArt,
            ResponseDetalleListaPrecios detalleLista
    ) {

        if (!detalleArt.isExito() || detalleArt.getArticulos() == null) {
            throw new ResourceNotFoundException("El articulo no existe.");
        }

        if (!detalleLista.isExito() || detalleLista.getListaPrecios() == null) {
            throw new ResourceNotFoundException("La ista de precios no existe.");
        }

    }
}
