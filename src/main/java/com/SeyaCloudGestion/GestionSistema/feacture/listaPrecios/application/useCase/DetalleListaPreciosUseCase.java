package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.services.ListaPreciosService;
import org.springframework.stereotype.Component;

@Component
public class DetalleListaPreciosUseCase {
    private final ListaPreciosService listaPreciosService;

    public DetalleListaPreciosUseCase(ListaPreciosService listaPreciosService) {
        this.listaPreciosService = listaPreciosService;
    }
    public ResponseDetalleListaPrecios DetalleListaPrecios(long idListaPrecio) {
        try {
            RequestDetalleListaPrecios request = new RequestDetalleListaPrecios();
            request.setIdListaPrecios(idListaPrecio);
            ResponseDetalleListaPrecios response = listaPreciosService.DetalleListaPrecios(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseDetalleListaPrecios response = new ResponseDetalleListaPrecios();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al obtener el detalle de la lista de precios: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseDetalleListaPrecios response = new ResponseDetalleListaPrecios();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}