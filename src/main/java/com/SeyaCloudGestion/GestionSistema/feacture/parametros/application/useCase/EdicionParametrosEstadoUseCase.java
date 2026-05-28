package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestEditarEstadoParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseEditarEstadoParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.services.ParametrosService;
import org.springframework.stereotype.Component;

@Component
public class EdicionParametrosEstadoUseCase {
    private final ParametrosService parametrosService;


    public EdicionParametrosEstadoUseCase(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }

    public ResponseEditarEstadoParametros AnularParametro(long idParametroSistema) {
        try {
            RequestEditarEstadoParametros request = new RequestEditarEstadoParametros();
            request.setIdParametros(idParametroSistema);

            ResponseEditarEstadoParametros response = parametrosService.EditarEstadoParametros(request, 0);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseEditarEstadoParametros response = new ResponseEditarEstadoParametros();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al actualizar el parámetro: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoParametros response = new ResponseEditarEstadoParametros();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }
    public ResponseEditarEstadoParametros ActivarParametro(long idParametroSistema) {
        try {
            RequestEditarEstadoParametros request = new RequestEditarEstadoParametros();
            request.setIdParametros(idParametroSistema);

            ResponseEditarEstadoParametros response = parametrosService.EditarEstadoParametros(request, 1);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseEditarEstadoParametros response = new ResponseEditarEstadoParametros();
            response.setExito(false);
            response.setMessage(e.getMessage());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al actualizar el parámetro: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoParametros response = new ResponseEditarEstadoParametros();
            response.setExito(false);
            response.setMessage(mensajeError);

            return response;
        }
    }
}