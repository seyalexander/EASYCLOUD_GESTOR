package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestRegistroListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseRegistroListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import org.springframework.stereotype.Component;

@Component
public class RegistroListaPreciosUseCase {
    private final ListaPreciosService listaPreciosService;

    public RegistroListaPreciosUseCase(ListaPreciosService listaPreciosService) {
        this.listaPreciosService = listaPreciosService;
    }
    public ResponseRegistroListaPrecios RegistroListaPrecios(RequestRegistroListaPrecios request) {
        try {
            ResponseRegistroListaPrecios response = listaPreciosService.RegistroListaPrecios(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseRegistroListaPrecios response = new ResponseRegistroListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al registrar la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseRegistroListaPrecios response = new ResponseRegistroListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}