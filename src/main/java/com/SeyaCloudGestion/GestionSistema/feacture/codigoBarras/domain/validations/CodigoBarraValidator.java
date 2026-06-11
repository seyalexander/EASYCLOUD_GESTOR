package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;

public class CodigoBarraValidator {

    public static void validarDependenciasParaRegistro(
            ResponseDetalleArticulo detalleArt
    ) {

        if (!detalleArt.isExito() || detalleArt.getArticulos() == null) {
            throw new ResourceNotFoundException("El articulo no existe.");
        }
    }
}
