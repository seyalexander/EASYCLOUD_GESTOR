package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request.RequestRegistroTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseRegistroTipoDocumento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.services.TipoDocumentoService;
import com.SeyaCloudGestion.GestionSistema.utils.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
public class RegistroTipoDocumentoUseCase {
    private final TipoDocumentoService tipoDocumentoService;

    public RegistroTipoDocumentoUseCase(
            TipoDocumentoService tipoDocumentoService
    ){
        this.tipoDocumentoService = tipoDocumentoService;
    }

    public ResponseRegistroTipoDocumento RegistroTipoDocumento(RequestRegistroTipoDocumento request) {
        try {
            // VALIDACIÓN DE CAMPOS
            if (request == null) {
                String mensajeError = "No se encontró datos para registrar";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getDescripcion() == null || request.getDescripcion().isEmpty()) {
                String mensajeError = "La descripción no puede estar vacía";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getTipoCaracter() == 0) {
                String mensajeError = "El tipo carácter no se está enviando correctamente";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getCodigoSunat() == null || request.getCodigoSunat().isEmpty()) {
                String mensajeError = "El Código SUNAT del documento no puede enviarse en vacío";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getLongitudMin() == 0) {
                String mensajeError = "La longitud mínima debe enviarse obligatoriamente";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getLongitudMax() == 0) {
                String mensajeError = "La longitud máxima debe enviarse obligatoriamente";
                throw new IllegalArgumentException(mensajeError);
            }

            if (request.getLongitudMin() > request.getLongitudMax()) {
                String mensajeError = "La longitud mínima no debe ser mayor a la longitud máxima";
                throw new IllegalArgumentException(mensajeError);
            }

            //long userId = SecurityUtils.getCurrentUserId();
            long userId = 1L;
            ResponseRegistroTipoDocumento response = tipoDocumentoService.RegistroTipoDocumento(request, userId);
            if(response.isExito()){

            }
            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseRegistroTipoDocumento response = new ResponseRegistroTipoDocumento();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al registrar el tipos de documentos: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseRegistroTipoDocumento response = new ResponseRegistroTipoDocumento();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
