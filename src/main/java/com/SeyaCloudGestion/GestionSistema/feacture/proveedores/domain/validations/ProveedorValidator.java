package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.domain.validations;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestEditarAllProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.request.RequestRegistroProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations.dependencias.RucValidator;

public class ProveedorValidator {
    //para insert
    public static void validarDatosRuc(RequestRegistroProveedor request) {

        String ruc = request.getRuc().trim();
        /*
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
         */

        if (RucValidator.esPersonaJuridica(ruc)) {
            if (request.getRazonSocial() == null || request.getRazonSocial().trim().isEmpty()) {
                throw new IllegalArgumentException("La razón social es obligatoria para una persona jurídica.");
            }
            /*
            if (request.getNombres() != null && !request.getNombres().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona jurídica no puede tener nombres.");
            }

            if (request.getApellidos() != null && !request.getApellidos().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona jurídica no puede tener apellidos.");
            }
             */
        }

    }
    /*
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
     */
    //para update
    public static void validarDatosRuc(RequestEditarAllProveedor request) {

        String ruc = request.getRuc().trim();
        /*
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
         */

        if (RucValidator.esPersonaJuridica(ruc)) {
            if (request.getRazonSocial() == null || request.getRazonSocial().trim().isEmpty()) {
                throw new IllegalArgumentException("La razón social es obligatoria para una persona jurídica.");
            }
            /*
            if (request.getNombres() != null && !request.getNombres().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona jurídica no puede tener nombres.");
            }

            if (request.getApellidos() != null && !request.getApellidos().trim().isEmpty()) {
                throw new IllegalArgumentException("Una persona jurídica no puede tener apellidos.");
            }
             */
        }

    }
    /*
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
     */
}
