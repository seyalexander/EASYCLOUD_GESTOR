package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestListaParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseListaParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.services.ParametrosService;
import org.springframework.stereotype.Component;

@Component
public class ListaParametrosUseCase {
    private final ParametrosService parametrosService;

    public ListaParametrosUseCase(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }

    public ResponseListaParametros ListaParametro(RequestListaParametros request) {
        try {
            ResponseListaParametros response = parametrosService.ListaParametros(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {

            ResponseListaParametros response = new ResponseListaParametros();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setParametros(java.util.List.of());

            return response;

        } catch (Exception e) {

            String mensajeError = "Error inesperado al listar los parámetros: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaParametros response = new ResponseListaParametros();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setParametros(java.util.List.of());

            return response;
        }
    }
}