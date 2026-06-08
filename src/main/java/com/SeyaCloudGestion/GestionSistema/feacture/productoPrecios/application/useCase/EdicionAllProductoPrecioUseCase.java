package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.useCase;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestEditarAllProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseEditarAllProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.services.ProductoPrecioService;
import org.springframework.stereotype.Component;

@Component
public class EdicionAllProductoPrecioUseCase {
    private  final ProductoPrecioService productoPrecioService;

    public EdicionAllProductoPrecioUseCase(ProductoPrecioService productoPrecioService) {
        this.productoPrecioService = productoPrecioService;
    }
    public ResponseEditarAllProductoPrecio EditarAllProductoPrecio(RequestEditarAllProductoPrecio request) {
        try {
            ResponseEditarAllProductoPrecio response = productoPrecioService.EditarAllProductoPrecio(request);

            if (response.isExito()) {}

            return response;

        } catch (IllegalArgumentException | SecurityException e) {
            ResponseEditarAllProductoPrecio response = new ResponseEditarAllProductoPrecio();
            response.setExito(false);
            response.setMessage(e.getMessage());
            return response;
        } catch (Exception e) {
            String mensajeError = "Error inesperado al actualizar el precio del producto: " + e.getMessage();
            System.err.println("[ERROR] " + mensajeError);

            ResponseEditarAllProductoPrecio response = new ResponseEditarAllProductoPrecio();
            response.setExito(false);
            response.setMessage(mensajeError);
            return response;
        }
    }
}
