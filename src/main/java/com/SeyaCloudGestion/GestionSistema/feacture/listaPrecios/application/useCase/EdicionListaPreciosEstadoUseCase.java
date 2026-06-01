package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestEditarEstadoListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseEditarEstadoListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import org.springframework.stereotype.Component;

@Component
public class EdicionListaPreciosEstadoUseCase {
    private final ListaPreciosService listaPreciosService;

    public EdicionListaPreciosEstadoUseCase(ListaPreciosService listaPreciosService) {
        this.listaPreciosService = listaPreciosService;
    }
    public ResponseEditarEstadoListaPrecios AnularListaPrecios(long idListaPrecio) {
        try {
            RequestEditarEstadoListaPrecios request = new RequestEditarEstadoListaPrecios();
            request.setIdListaPrecios(idListaPrecio);

            ResponseEditarEstadoListaPrecios response = listaPreciosService.EditarEstadoListaPrecios(request, 0);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoListaPrecios response = new ResponseEditarEstadoListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoListaPrecios response = new ResponseEditarEstadoListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }

    public ResponseEditarEstadoListaPrecios ActivarListaPrecios(long idListaPrecio) {
        try {
            RequestEditarEstadoListaPrecios request = new RequestEditarEstadoListaPrecios();
            request.setIdListaPrecios(idListaPrecio);

            ResponseEditarEstadoListaPrecios response = listaPreciosService.EditarEstadoListaPrecios(request, 1);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarEstadoListaPrecios response = new ResponseEditarEstadoListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarEstadoListaPrecios response = new ResponseEditarEstadoListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}