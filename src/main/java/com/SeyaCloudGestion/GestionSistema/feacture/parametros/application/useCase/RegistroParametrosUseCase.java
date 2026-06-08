package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestRegistroParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseRegistroParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.services.ParametrosService;
import org.springframework.stereotype.Component;

@Component
public class RegistroParametrosUseCase {
    private final ParametrosService parametrosService;

    public RegistroParametrosUseCase(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }
    public ResponseRegistroParametros RegistroParametro(RequestRegistroParametros request) {
        try {
            ResponseRegistroParametros response = parametrosService.RegistroParametros(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseRegistroParametros response = new ResponseRegistroParametros();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al registrar el parámetro: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroParametros response = new ResponseRegistroParametros();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }
}