package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations.dependencias;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoCaracter;

public class TipoDocumentoValidator {

    public static void validarDocumento(String documento, ResponseDetalleTipoDocumento responseTipoDocumento) {

        int longMin = responseTipoDocumento.getTipoDocumento().getLongitudMin();
        int longMax = responseTipoDocumento.getTipoDocumento().getLongitudMax();

        documento = documento.trim();

        if (longMin != longMax && (documento.length() < longMin || documento.length() > longMax)) {
            throw new IllegalArgumentException("El N° de documento debe estar entre " + longMin + " y " + longMax + " caracteres.");
        }

        if (longMin == longMax && documento.length() != longMax) {
            throw new IllegalArgumentException("El N° de documento debe tener " + longMax + " caracteres.");
        }
        TipoCaracter tipoCaracter = responseTipoDocumento.getTipoDocumento().getTipoCaracter();

        if (!tipoCaracter.validar(documento)) {
            throw new IllegalArgumentException(tipoCaracter.getMensajeError());
        }
    }

}
