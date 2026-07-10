package com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.validations;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response.ResponseDetalleMarca;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response.ResponseDetalleSubFamilia;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response.ResponseDetalleUnidadMedida;

public class ValidacionRequest_RegistrarArticulo {

    public static void validarRegistroArticulo(
            ResponseDetalleSubFamilia detalleSub,
            ResponseDetalleMarca detalleMarca,
            ResponseDetalleUnidadMedida detalleUnidad
    ) {

        if (!detalleSub.isExito() || detalleSub.getSubFamilia() == null) {
            throw new IllegalArgumentException("La subfamilia no existe.");
        }

        if (!detalleMarca.isExito() || detalleMarca.getMarca() == null) {
            throw new IllegalArgumentException("La marca no existe.");
        }

        if (!detalleUnidad.isExito() || detalleUnidad.getUnidadMedida() == null) {
            throw new IllegalArgumentException("La unidad de medida no existe.");
        }
    }
}
