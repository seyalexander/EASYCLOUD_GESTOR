package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestListarListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseListaListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import org.springframework.stereotype.Component;

@Component
public class ListaListaPreciosUseCase {
    private final ListaPreciosService listaPreciosService;

    public ListaListaPreciosUseCase(ListaPreciosService listaPreciosService) {
        this.listaPreciosService = listaPreciosService;
    }

    public ResponseListaListaPrecios listaListaPrecios(RequestListarListaPrecios request) {
        try {
            ResponseListaListaPrecios response = listaPreciosService.ListarListaPrecios(request);
            if(response.isExito()){

            }

            return response;

        }catch (IllegalArgumentException | SecurityException e){
            ResponseListaListaPrecios response = new ResponseListaListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setListaPrecios(java.util.List.of());
            return response;
        }
        catch (Exception e){
            String mensajeError = "Error inesperado al listar las listas de precio: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);
            ResponseListaListaPrecios response = new ResponseListaListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setListaPrecios(java.util.List.of());
            return response;
        }
    }
}
