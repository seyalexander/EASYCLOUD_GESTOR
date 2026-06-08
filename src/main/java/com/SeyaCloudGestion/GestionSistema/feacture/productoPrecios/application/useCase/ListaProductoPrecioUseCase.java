package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestListaProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseListaProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import org.springframework.stereotype.Component;

@Component
public class ListaProductoPrecioUseCase {
    private  final ProductoPrecioService productoPrecioService;

    public ListaProductoPrecioUseCase(ProductoPrecioService productoPrecioService) {
        this.productoPrecioService = productoPrecioService;
    }
    public ResponseListaProductoPrecio ListarProductoPrecio(RequestListaProductoPrecio request) {
        try {
            ResponseListaProductoPrecio response = productoPrecioService.ListaProductoPrecio(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseListaProductoPrecio response = new ResponseListaProductoPrecio();
            response.setExito(false);
            response.setMessage(e.getMessage());
            response.setProductoPrecios(java.util.List.of());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al listar los precios de productos: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseListaProductoPrecio response = new ResponseListaProductoPrecio();
            response.setExito(false);
            response.setMessage(mensajeError);
            response.setProductoPrecios(java.util.List.of());
            return response;
        }
    }
}
