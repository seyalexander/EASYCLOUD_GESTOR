package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseEditarAllListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllListaPreciosUseCase {
    private final ListaPreciosService listaPreciosService;

    public EdicionAllListaPreciosUseCase(ListaPreciosService listaPreciosService) {
        this.listaPreciosService = listaPreciosService;
    }
    public ResponseEditarAllListaPrecios EditarAllListaPrecios(RequestEditarAllListaPrecios request) {
        try {
            ResponseEditarAllListaPrecios response = listaPreciosService.EditarAllListaPrecios(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllListaPrecios response = new ResponseEditarAllListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllListaPrecios response = new ResponseEditarAllListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}