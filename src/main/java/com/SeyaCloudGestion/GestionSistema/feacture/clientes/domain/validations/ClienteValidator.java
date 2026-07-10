package com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.validations;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestRegistroCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations.dependencias.RucValidator;

public class ClienteValidator {
    //para insert
    public static void validarDatosRuc(RequestRegistroCliente request) {

        String ruc = request.getNumeroDocumento().trim();

        if (RucValidator.esPersonaNatural(ruc)) {
            if (request.getNombres() == null || request.getNombres().trim().isEmpty()) {
                throw new IllegalArgumentException("Los nombres son obligatorios para una persona natural.");
            }

            if (request.getApellidos() == null || request.getApellidos().trim().isEmpty()) {
                throw new IllegalArgumentException("Los apellidos son obligatorios para una persona natural.");
            }

            if (request.getRazonSocial() != null && !request.getRazonSocial().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona natural no puede tener razón social.");
            }

        }

        if (RucValidator.esPersonaJuridica(ruc)) {
            if (request.getRazonSocial() == null || request.getRazonSocial().trim().isEmpty()) {
                throw new IllegalArgumentException("La razón social es obligatoria para una persona jurídica.");
            }

            if (request.getNombres() != null && !request.getNombres().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona jurídica no puede tener nombres.");
            }

            if (request.getApellidos() != null && !request.getApellidos().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona jurídica no puede tener apellidos.");
            }

        }

    }
    public static void validarDatosDni(RequestRegistroCliente requestCliente){
            if (requestCliente.getNombres() == null || requestCliente.getNombres().trim().isEmpty()) {
                throw new IllegalArgumentException("Los nombres son obligatorios.");
            }

            if (requestCliente.getApellidos() == null || requestCliente.getApellidos().trim().isEmpty()) {
                throw new IllegalArgumentException("Los apellidos son obligatorios.");
            }

            if (requestCliente.getRazonSocial() != null && !requestCliente.getRazonSocial().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona no puede tener razón social.");
        }
    }
    //para update
    public static void validarDatosRuc(RequestEditarAllCliente request) {

        String ruc = request.getNumeroDocumento().trim();

        if (RucValidator.esPersonaNatural(ruc)) {
            if (request.getNombres() == null || request.getNombres().trim().isEmpty()) {
                throw new IllegalArgumentException("Los nombres son obligatorios para una persona natural.");
            }

            if (request.getApellidos() == null || request.getApellidos().trim().isEmpty()) {
                throw new IllegalArgumentException("Los apellidos son obligatorios para una persona natural.");
            }

            if (request.getRazonSocial() != null && !request.getRazonSocial().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona natural no puede tener razón social.");
            }

        }

        if (RucValidator.esPersonaJuridica(ruc)) {
            if (request.getRazonSocial() == null || request.getRazonSocial().trim().isEmpty()) {
                throw new IllegalArgumentException("La razón social es obligatoria para una persona jurídica.");
            }

            if (request.getNombres() != null && !request.getNombres().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona jurídica no puede tener nombres.");
            }

            if (request.getApellidos() != null && !request.getApellidos().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona jurídica no puede tener apellidos.");
            }

        }

    }
    public static void validarDatosDni(RequestEditarAllCliente requestCliente){
        if (requestCliente.getNombres() == null || requestCliente.getNombres().trim().isEmpty()) {
            throw new IllegalArgumentException("Los nombres son obligatorios.");
        }

        if (requestCliente.getApellidos() == null || requestCliente.getApellidos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios.");
        }

        if (requestCliente.getRazonSocial() != null && !requestCliente.getRazonSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("Una persona no puede tener razón social.");
        }
    }
}
