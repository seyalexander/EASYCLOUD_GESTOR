package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.validations;

import com.SeyaCloudGestion.GestionSistema.common.exceptions.ResourceNotFoundException;
import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.response.ResponseDetalleSucursales;

public class ValidacionRequest_RegistrarAlmacen {
    public static void validarRegistro(
            ResponseDetalleSucursales detalleSuc
    ) {

        if (!detalleSuc.isExito() || detalleSuc.getSucursales() == null) {
            throw new ResourceNotFoundException("La sucursal no existe.");
        }

    }
}
