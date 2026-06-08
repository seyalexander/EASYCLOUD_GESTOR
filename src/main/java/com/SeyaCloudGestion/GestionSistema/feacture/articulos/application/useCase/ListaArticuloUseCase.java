package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestListaArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseListaArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.services.ArticulosService;
import org.springframework.stereotype.Component;

@Component
public class ListaArticuloUseCase {
    private final ArticulosService articulosService;

    public ListaArticuloUseCase(ArticulosService articulosService) {
        this.articulosService = articulosService;
    }
    public ResponseListaArticulo ListaArticulo(RequestListaArticulo request) {
        try {
            ResponseListaArticulo response = articulosService.ListaArticulos(request);

            if (response.isExito()) {
            }

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaArticulo response = new ResponseListaArticulo();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setArticulos(java.util.List.of());
            return response;

        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los artículos: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaArticulo response = new ResponseListaArticulo();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setArticulos(java.util.List.of());
            return response;
        }
    }
}
