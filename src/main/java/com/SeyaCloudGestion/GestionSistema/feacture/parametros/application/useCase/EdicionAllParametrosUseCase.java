package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestEditarAllParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseEditarAllParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.services.ParametrosService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllParametrosUseCase {
    private final ParametrosService parametrosService;

    public EdicionAllParametrosUseCase(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }

    public ResponseEditarAllParametros EditarAllParametros(RequestEditarAllParametros request) {
        try {
            ResponseEditarAllParametros response = parametrosService.EditarAllParametros(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseEditarAllParametros response = new ResponseEditarAllParametros();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al editar los parámetros: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllParametros response = new ResponseEditarAllParametros();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }
}